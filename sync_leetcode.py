import os
import requests
import json
import re

BASE_URL = "https://leetcode.com"
SESSION = os.getenv("LEETCODE_SESSION")

HEADERS = {
    "Content-Type": "application/json",
    "Cookie": f"LEETCODE_SESSION={SESSION}" if SESSION else ""
}

# ---------------------------
# Utils
# ---------------------------

def format_id(pid: str):
    return str(pid).zfill(4)

# ---------------------------
# Fetch Latest Accepted Submission
# ---------------------------

def fetch_latest_submission():
    res = requests.get(f"{BASE_URL}/api/submissions/", headers=HEADERS)
    subs = res.json().get("submissions_dump", [])

    for sub in subs:
        if sub["status_display"] == "Accepted":
            return sub
    return None

# ---------------------------
# ✅ FIXED: Percentile Extraction
# ---------------------------

def fetch_submission_percentile(submission_id):
    url = f"{BASE_URL}/submissions/detail/{submission_id}/"
    res = requests.get(url, headers=HEADERS)

    if res.status_code != 200:
        return {"runtime_percent": "", "memory_percent": ""}

    html = res.text

    runtime_match = re.search(r'"runtime_percentile":\s*([\d.]+)', html)
    memory_match = re.search(r'"memory_percentile":\s*([\d.]+)', html)

    return {
        "runtime_percent": runtime_match.group(1) if runtime_match else "",
        "memory_percent": memory_match.group(1) if memory_match else ""
    }

# ---------------------------
# Fetch Problem Details
# ---------------------------

def fetch_problem_details(slug):
    query = {
        "query": """
        query getQuestion($titleSlug: String!) {
          question(titleSlug: $titleSlug) {
            questionFrontendId
            title
            difficulty
            content
            topicTags {
              name
            }
          }
        }
        """,
        "variables": {"titleSlug": slug}
    }

    res = requests.post(f"{BASE_URL}/graphql", json=query, headers=HEADERS)
    q = res.json()["data"]["question"]

    return {
        "id": q["questionFrontendId"],
        "title": q["title"],
        "difficulty": q["difficulty"],
        "description": q["content"],
        "tags": [t["name"] for t in q["topicTags"]]
    }

# ---------------------------
# ✅ FIXED: README Update (robust)
# ---------------------------

def normalize(tag):
    return tag.lower().replace(" ", "")


def update_root_readme(problem, slug):
    path = "README.md"

    # IMPORTANT: match your repo format
    pid = str(problem["id"]).zfill(4)
    entry = f"| [{pid}-{slug}](https://github.com/imswapnilpatange/leetcode/tree/master/{pid}-{slug}) |"

    with open(path, "r") as f:
        content = f.read()

    start_marker = "<!---LeetCode Topics Start-->"
    end_marker = "<!---LeetCode Topics End-->"

    if start_marker not in content or end_marker not in content:
        print("Markers not found. Aborting update.")
        return

    before = content.split(start_marker)[0]
    middle = content.split(start_marker)[1].split(end_marker)[0]
    after = content.split(end_marker)[1]

    lines = middle.strip().split("\n")

    # ---------------------------
    # Parse sections
    # ---------------------------
    sections = {}
    order = []
    current = None

    for line in lines:
        if line.startswith("## "):
            current = line[3:].strip()
            sections[current] = []
            order.append(current)
        elif current:
            sections[current].append(line)

    # ---------------------------
    # Normalize section names
    # ---------------------------
    normalized_map = {normalize(k): k for k in sections}

    # ---------------------------
    # Insert into tags
    # ---------------------------
    for tag in problem["tags"]:
        key = normalize(tag)

        if key in normalized_map:
            section_name = normalized_map[key]
        else:
            # Create new section with table header
            section_name = tag
            sections[section_name] = ["|  |", "| ------- |"]
            order.append(section_name)
            normalized_map[key] = section_name

        # Avoid duplicate
        if entry not in sections[section_name]:
            sections[section_name].append(entry)

    # ---------------------------
    # Rebuild middle content
    # ---------------------------
    new_middle = []

    for sec in order:
        new_middle.append(f"## {sec}")
        for row in sections[sec]:
            new_middle.append(row)
        new_middle.append("")  # spacing

    new_middle_str = "\n".join(new_middle).strip()

    # ---------------------------
    # Final content
    # ---------------------------
    new_content = (
        before
        + start_marker + "\n"
        + new_middle_str + "\n"
        + end_marker
        + after
    )

    with open(path, "w") as f:
        f.write(new_content)

# ---------------------------
# Create Files
# ---------------------------

def create_files(problem, submission, perc):
    pid = format_id(problem["id"])
    slug = submission["title_slug"]

    folder = f"{pid}-{slug}"
    os.makedirs(folder, exist_ok=True)

    ext_map = {"java": "java", "python": "py", "cpp": "cpp"}
    ext = ext_map.get(submission["lang"].lower(), "txt")

    filename = f"{pid}-{slug}.{ext}"

    with open(f"{folder}/{filename}", "w") as f:
        f.write(submission["code"])

    with open(f"{folder}/README.md", "w") as f:
        f.write(f"""# {problem['title']}

- Problem ID: {problem['id']}
- Difficulty: {problem['difficulty']}
- Tags: {", ".join(problem['tags'])}

## Description
{problem['description']}

## Link
https://leetcode.com/problems/{slug}
""")

    metadata = {
        "problem_id": problem["id"],
        "problem_name": problem["title"],
        "difficulty": problem["difficulty"],
        "tags": problem["tags"],
        "language": submission["lang"],
        "runtime": submission["runtime"],
        "runtime_percent": perc["runtime_percent"],
        "memory": submission["memory"],
        "memory_percent": perc["memory_percent"],
        "link": f"https://leetcode.com/problems/{slug}"
    }

    with open(f"{folder}/metadata.json", "w") as f:
        json.dump(metadata, f, indent=2)

    return metadata

# ---------------------------
# Main
# ---------------------------

def main():
    os.system("git config user.name 'github-actions'")
    os.system("git config user.email 'github-actions@github.com'")

    latest = fetch_latest_submission()
    if not latest:
        return

    if os.path.exists(".last_synced"):
        if open(".last_synced").read().strip() == str(latest["id"]):
            print("No new submission")
            return

    slug = latest["title_slug"]

    problem = fetch_problem_details(slug)
    perc = fetch_submission_percentile(latest["id"])

    metadata = create_files(problem, latest, perc)

    update_root_readme(problem, slug)

    with open(".last_synced", "w") as f:
        f.write(str(latest["id"]))

    os.system("git add .")

    commit_msg = f"Time: {metadata['runtime']} ({metadata['runtime_percent']}%), Space: {metadata['memory']} ({metadata['memory_percent']}%) - Sync"

    os.system(f'git commit -m "{commit_msg}" || echo "No changes"')

    if os.system("git pull --rebase origin main") != 0:
        os.system("git rebase --abort")
        return

    os.system("git push origin main")


if __name__ == "__main__":
    main()
