SELECT DISTINCT viewer_id AS id
FROM views
WHERE author_id = viewer_id
ORDER BY id;