CREATE TRIGGER trg_post_like_delete
    AFTER DELETE ON likes
    FOR EACH ROW
BEGIN
    IF OLD.type = 'POST' THEN
        UPDATE post
        SET likes_count = likes_count - 1
        WHERE id = OLD.target_id;
    END IF;
END;
