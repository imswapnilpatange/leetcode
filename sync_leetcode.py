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

def get_date_range(start_date: str, end_date: str) -> List[str]:
    start = datetime.datetime.strptime(start_date, "%Y-%m-%d")
    end = datetime.datetime.strptime(end_date, "%Y-%m-%d")
    return [(start + datetime.timedelta(days=i)).strftime("%Y-%m-%d")
            for i in range((end - start).days + 1)]

def fetch_daily_problem(date: str):
    query = {
        "query": """
        query questionOfToday {
          activeDailyCodingChallengeQuestion {
            date
            question {
              questionId
              title
              titleSlug
              difficulty
              content
              topicTags {
                name
              }
            }
          }
        }
        """
    }

    res = requests.post(BASE_URL, json=query, headers=HEADERS)
    data = res.json()

    q = data["data"]["activeDailyCodingChallengeQuestion"]["question"]

    return {
        "id": q["questionId"],
        "title": q["title"],
        "slug": q["titleSlug"],
        "difficulty": q["difficulty"],
        "description": q["content"],
        "tags": [t["name"] for t in q["topicTags"]]
    }

def fetch_latest_submission(slug: str):
    url = f"https://leetcode.com/api/submissions/{slug}/"
    res = requests.get(url, headers=HEADERS)

    if res.status_code != 200:
        return None

    data = res.json()
    subs = data.get("submissions_dump", [])

    for sub in subs:
        if sub["status_display"] == "Accepted":
            return {
                "code": sub["code"],
                "lang": sub["lang"],
                "runtime": sub["runtime"],
                "memory": sub["memory"]
            }
    return None

def clean_name(name: str):
    return re.sub(r'[^a-zA-Z0-9\- ]', '', name).replace(" ", "-").lower()

def create_files(date, problem, submission):
    folder = f"Difficulty-{problem['difficulty']}/{date}-{clean_name(problem['title'])}"
    os.makedirs(folder, exist_ok=True)

    ext_map = {"java": "java", "python": "py", "cpp": "cpp"}
    lang = submission["lang"].lower()
    ext = ext_map.get(lang, "txt")

    # Solution file
    with open(f"{folder}/{problem['slug']}.{ext}", "w", encoding="utf-8") as f:
        f.write(submission["code"])

    # README
    readme = f"""# {problem['title']}

- Problem ID: {problem['id']}
- Difficulty: {problem['difficulty']}
- Tags: {", ".join(problem['tags'])}

## Description
{problem['description']}

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

def main():
    today = datetime.date.today().strftime("%Y-%m-%d")

    start_date = os.getenv("START_DATE", today)
    end_date = os.getenv("END_DATE", today)

    dates = get_date_range(start_date, end_date)

    for date in dates:
        problem = fetch_daily_problem(date)
        submission = fetch_latest_submission(problem["slug"])

        if not submission:
            print(f"No accepted submission for {problem['title']}")
            continue

        folder = create_files(date, problem, submission)

        commit_msg = f"{problem['title']} (#{problem['id']}) | Time: ({submission['runtime']}) , Space: ({submission['memory']}) | Tags: {', '.join(problem['tags'])}"

        os.system("git config user.name 'github-actions'")
        os.system("git config user.email 'github-actions@github.com'")
        os.system(f"git add {folder}")
        os.system(f'git commit -m "{commit_msg}" || echo "No changes"')
        os.system("git push")

if __name__ == "__main__":
    main()
