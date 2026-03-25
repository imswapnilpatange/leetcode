import os
import requests
import datetime
import json
import re
from typing import List

BASE_URL = "https://leetcode.com/graphql"
SESSION = os.getenv("LEETCODE_SESSION")

HEADERS = {
    "Content-Type": "application/json",
    "Cookie": f"LEETCODE_SESSION={SESSION}" if SESSION else ""
}

# ---------------------------
# Utils
# ---------------------------

def get_date_range(start_date: str, end_date: str) -> List[str]:
    start = datetime.datetime.strptime(start_date, "%Y-%m-%d")
    end = datetime.datetime.strptime(end_date, "%Y-%m-%d")
    return [(start + datetime.timedelta(days=i)).strftime("%Y-%m-%d")
            for i in range((end - start).days + 1)]

def clean_name(name: str):
    return re.sub(r'[^a-zA-Z0-9\- ]', '', name).replace(" ", "-").lower()

# ---------------------------
# LeetCode APIs
# ---------------------------

def fetch_daily_problem_by_date(target_date: str):
    year = int(target_date[:4])

    query = {
        "query": """
        query dailyProblems($year: Int!) {
          dailyCodingChallengeV2(year: $year) {
            challenges {
              date
              question {
                questionId
                questionFrontendId
                title
                titleSlug
                difficulty
                topicTags {
                  name
                }
              }
            }
          }
        }
        """,
        "variables": {"year": year}
    }

    res = requests.post(BASE_URL, json=query, headers=HEADERS)
    if res.status_code != 200:
        raise Exception(f"GraphQL failed: {res.status_code}")

    challenges = res.json()["data"]["dailyCodingChallengeV2"]["challenges"]

    for c in challenges:
        if c["date"] == target_date:
            q = c["question"]
            return {
                "id": q["questionFrontendId"],
                "title": q["title"],
                "slug": q["titleSlug"],
                "difficulty": q["difficulty"],
                "tags": [t["name"] for t in q["topicTags"]]
            }

    return None


def fetch_full_description(slug: str):
    query = {
        "query": """
        query getQuestion($titleSlug: String!) {
          question(titleSlug: $titleSlug) {
            content
          }
        }
        """,
        "variables": {"titleSlug": slug}
    }

    res = requests.post(BASE_URL, json=query, headers=HEADERS)
    if res.status_code != 200:
        return ""

    return res.json()["data"]["question"]["content"]


def fetch_latest_submission(slug: str):
    url = f"https://leetcode.com/api/submissions/{slug}/"
    res = requests.get(url, headers=HEADERS)

    if res.status_code != 200:
        return None

    subs = res.json().get("submissions_dump", [])

    for sub in subs:
        if sub["status_display"] == "Accepted":
            return {
                "code": sub["code"],
                "lang": sub["lang"],
                "runtime": sub["runtime"],
                "memory": sub["memory"]
            }
    return None

# ---------------------------
# File Creation
# ---------------------------

def create_files(date, problem, submission):
    folder = f"Difficulty-{problem['difficulty']}/{date}-{clean_name(problem['title'])}"
    os.makedirs(folder, exist_ok=True)

    ext_map = {"java": "java", "python": "py", "cpp": "cpp"}
    lang = submission["lang"].lower()
    ext = ext_map.get(lang, "txt")

    # Solution
    with open(f"{folder}/{problem['slug']}.{ext}", "w", encoding="utf-8") as f:
        f.write(submission["code"])

    description = fetch_full_description(problem["slug"])

    # README
    readme = f"""# {problem['title']}

- Problem ID: {problem['id']}
- Difficulty: {problem['difficulty']}
- Tags: {", ".join(problem['tags'])}

## Description
{description}

## Link
https://leetcode.com/problems/{problem['slug']}

## Complexity
- Time: TBD
- Space: TBD
"""
    with open(f"{folder}/README.md", "w", encoding="utf-8") as f:
        f.write(readme)

    # Metadata
    metadata = {
        "date": date,
        "problem_id": problem["id"],
        "problem_name": problem["title"],
        "difficulty": problem["difficulty"],
        "tags": problem["tags"],
        "language": submission["lang"],
        "runtime": submission["runtime"],
        "runtime_percent": "",
        "memory": submission["memory"],
        "memory_percent": "",
        "link": f"https://leetcode.com/problems/{problem['slug']}"
    }

    with open(f"{folder}/metadata.json", "w") as f:
        json.dump(metadata, f, indent=2)

    return folder

# ---------------------------
# Main
# ---------------------------

def main():
    today = datetime.date.today().strftime("%Y-%m-%d")

    start_date = os.getenv("START_DATE") or today
    end_date = os.getenv("END_DATE") or today

    dates = get_date_range(start_date, end_date)

    os.system("git config user.name 'github-actions'")
    os.system("git config user.email 'github-actions@github.com'")

    changed = False
    commit_parts = []

    for date in dates:
        try:
            problem = fetch_daily_problem_by_date(date)
            if not problem:
                print(f"No problem for {date}")
                continue

            submission = fetch_latest_submission(problem["slug"])
            if not submission:
                print(f"No accepted submission for {problem['title']}")
                continue

            folder = create_files(date, problem, submission)
            os.system(f"git add {folder}")

            changed = True
            commit_parts.append(f"{problem['title']} (#{problem['id']})")

        except Exception as e:
            print(f"Error for {date}: {e}")

    if not changed:
        print("No changes to commit")
        return

    # Single Commit Message
    commit_msg = f"LeetCode Sync: {start_date} → {end_date}\n" + "\n".join(commit_parts)

    os.system(f'git commit -m "{commit_msg}" || echo "No changes"')

    # Rebase-safe push
    pull_status = os.system("git pull --rebase origin main")

    if pull_status != 0:
        print("Rebase failed, aborting...")
        os.system("git rebase --abort")
        return

    os.system("git push origin main")


if __name__ == "__main__":
    main()
