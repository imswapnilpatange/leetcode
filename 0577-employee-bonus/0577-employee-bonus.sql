SELECT e.name, b.bonus
FROM employee e
LEFT OUTER JOIN bonus b
ON e.empid = b.empid
WHERE b.bonus < 1000
OR b.bonus IS NULL;