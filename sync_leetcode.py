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
    url = f"{BASE_URL}/api/submissions/"
    res = requests.get(url, headers=HEADERS)

    if res.status_code != 200:
        raise Exception("Failed to fetch submissions")

    subs = res.json().get("submissions_dump", [])

    for sub in subs:
        if sub["status_display"] == "Accepted":
            return sub

    return None

# ---------------------------
# Fetch Submission Detail (Accurate %)
# ---------------------------

def fetch_submission_detail(submission_id):
    url = f"{BASE_URL}/api/submissions/{submission_id}/"
    res = requests.get(url, headers=HEADERS)

    if res.status_code != 200:
        return {}

    data = res.json()

    return {
        "runtime_percent": data.get("runtime_percentile", ""),
        "memory_percent": data.get("memory_percentile", "")
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

    if res.status_code != 200:
        raise Exception("Failed to fetch problem")

    q = res.json()["data"]["question"]

    return {
        "id": q["questionFrontendId"],
        "title": q["title"],
        "difficulty": q["difficulty"],
        "description": q["content"],
        "tags": [t["name"] for t in q["topicTags"]]
    }

# ---------------------------
# Update Root README (Append at END)
# ---------------------------

def update_root_readme(problem, slug):
    line = f"- [{problem['title']}](https://leetcode.com/problems/{slug})\n"
    readme_path = "README.md"

    if not os.path.exists(readme_path):
        with open(readme_path, "w") as f:
            f.write("# LeetCode Solutions\n\n## Related Topics\n\n")

    with open(readme_path, "r") as f:
        content = f.readlines()

    if line in content:
        return

    new_content = []
    in_section = False

    for i, l in enumerate(content):
        new_content.append(l)

        if "## Related Topics" in l:
            in_section = True
            continue

        # Append at end of section
        if in_section and (i + 1 == len(content) or content[i + 1].startswith("## ")):
            new_content.append(line)
            in_section = False

    if not any("## Related Topics" in l for l in content):
        new_content.append("\n## Related Topics\n\n")
        new_content.append(line)

    with open(readme_path, "w") as f:
        f.writelines(new_content)

# ---------------------------
# Create Files (Exact Structure)
# ---------------------------

def create_files(problem, submission, perc):
    pid = format_id(problem["id"])
    slug = submission["title_slug"]

    folder = f"{pid}-{slug}"
    os.makedirs(folder, exist_ok=True)

    ext_map = {"java": "java", "python": "py", "cpp": "cpp"}
    lang = submission["lang"].lower()
    ext = ext_map.get(lang, "txt")

    # Solution filename: id-slug.ext
    filename = f"{pid}-{slug}.{ext}"

    with open(f"{folder}/{filename}", "w", encoding="utf-8") as f:
        f.write(submission["code"])

    # README
    readme = f"""# {problem['title']}

- Problem ID: {problem['id']}
- Difficulty: {problem['difficulty']}
- Tags: {", ".join(problem['tags'])}

## Description
{problem['description']}

## Link
https://leetcode.com/problems/{slug}
"""
    with open(f"{folder}/README.md", "w", encoding="utf-8") as f:
        f.write(readme)

    # Metadata
    metadata = {
        "problem_id": problem["id"],
        "problem_name": problem["title"],
        "difficulty": problem["difficulty"],
        "tags": problem["tags"],
        "language": submission["lang"],
        "runtime": submission["runtime"],
        "runtime_percent": perc.get("runtime_percent", ""),
        "memory": submission["memory"],
        "memory_percent": perc.get("memory_percent", ""),
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

    # Idempotency
    if os.path.exists(".last_synced"):
        if open(".last_synced").read().strip() == str(latest["id"]):
            print("No new submission")
            return

    slug = latest["title_slug"]

    problem = fetch_problem_details(slug)
    perc = fetch_submission_detail(latest["id"])

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
