CREATE TRIGGER trg_post_like_insert
    AFTER INSERT ON likes
    FOR EACH ROW
BEGIN
    IF NEW.type = 'POST' THEN
        UPDATE post
        SET likes_count = likes_count + 1
        WHERE id = NEW.target_id;
    END IF;
END;
