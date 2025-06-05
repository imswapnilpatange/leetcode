SELECT today.id
FROM weather today
JOIN weather yesterday
ON today.recorddate = yesterday.recorddate + 1
WHERE today.temperature > yesterday.temperature;