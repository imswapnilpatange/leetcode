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

def clean_name(name: str):
    return re.sub(r'[^a-zA-Z0-9\- ]', '', name).replace(" ", "-").lower()

# ---------------------------
# Fetch Latest Accepted Submission
# ---------------------------

def fetch_latest_submission():
    url = f"{BASE_URL}/api/submissions/"
    res = requests.get(url, headers=HEADERS)

    if res.status_code != 200:
        raise Exception("Failed to fetch submissions (session expired?)")

    subs = res.json().get("submissions_dump", [])

    for sub in subs:
        if sub["status_display"] == "Accepted":
            return sub

    return None

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
        raise Exception("Failed to fetch problem details")

    q = res.json()["data"]["question"]

    return {
        "id": q["questionFrontendId"],
        "title": q["title"],
        "difficulty": q["difficulty"],
        "description": q["content"],
        "tags": [t["name"] for t in q["topicTags"]]
    }

# ---------------------------
# File Creation (Overwrite)
# ---------------------------

def create_files(problem, submission):
    folder = f"Difficulty-{problem['difficulty']}/{clean_name(problem['title'])}"
    os.makedirs(folder, exist_ok=True)

    ext_map = {"java": "java", "python": "py", "cpp": "cpp"}
    lang = submission["lang"].lower()
    ext = ext_map.get(lang, "txt")

    # Solution
    with open(f"{folder}/{submission['title_slug']}.{ext}", "w", encoding="utf-8") as f:
        f.write(submission["code"])

    # README
    readme = f"""# {problem['title']}

- Problem ID: {problem['id']}
- Difficulty: {problem['difficulty']}
- Tags: {", ".join(problem['tags'])}

## Description
{problem['description']}

## Link
https://leetcode.com/problems/{submission['title_slug']}

## Complexity
- Time: TBD
- Space: TBD
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
        "runtime_percent": "",
        "memory": submission["memory"],
        "memory_percent": "",
        "link": f"https://leetcode.com/problems/{submission['title_slug']}"
    }

    with open(f"{folder}/metadata.json", "w") as f:
        json.dump(metadata, f, indent=2)

    return folder

# ---------------------------
# Main
# ---------------------------

def main():
    os.system("git config user.name 'github-actions'")
    os.system("git config user.email 'github-actions@github.com'")

    latest = fetch_latest_submission()

    if not latest:
        print("No accepted submission found")
        return

    slug = latest["title_slug"]

    # Prevent duplicate work (idempotent)
    if os.path.exists(f".last_synced") :
        with open(".last_synced", "r") as f:
            if f.read().strip() == str(latest["id"]):
                print("No new submission")
                return

    problem = fetch_problem_details(slug)
    folder = create_files(problem, latest)

    # Save last synced ID
    with open(".last_synced", "w") as f:
        f.write(str(latest["id"]))

    # Commit
    os.system("git add .")
    commit_msg = f"{problem['title']} (#{problem['id']}) | Time: {latest['runtime']} | Space: {latest['memory']}"
    os.system(f'git commit -m "{commit_msg}" || echo "No changes"')

    # Rebase-safe push
    pull_status = os.system("git pull --rebase origin main")
    if pull_status != 0:
        os.system("git rebase --abort")
        return

    os.system("git push origin main")


if __name__ == "__main__":
    main()
