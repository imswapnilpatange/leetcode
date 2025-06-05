SELECT s.student_id, s.student_name, su.subject_name, count(e.student_id) AS attended_exams
FROM students s
CROSS JOIN subjects su
LEFT JOIN examinations e
    ON s.student_id = e.student_id
    AND su.subject_name = e.subject_name
GROUP BY s.student_id, s.student_name, su.subject_name
ORDER BY s.student_id, su.subject_name;