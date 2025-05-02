CREATE TRIGGER trg_comment_like_insert
    AFTER INSERT ON likes
    FOR EACH ROW
BEGIN
    IF NEW.type = 'COMMENT' THEN
        UPDATE comment
        SET likes_count = likes_count + 1
        WHERE id = NEW.target_id;
    END IF;
END;
