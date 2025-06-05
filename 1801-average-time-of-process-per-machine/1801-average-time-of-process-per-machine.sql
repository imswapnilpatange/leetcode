SELECT a.machine_id, ROUND(AVG(ac.timestamp - a.timestamp),3) AS processing_time
FROM activity a
JOIN activity ac
ON a.machine_id = ac.machine_id
AND a.process_id = ac.process_id
AND a.activity_type = 'start'
AND ac.activity_type = 'end'
GROUP BY a.machine_id;