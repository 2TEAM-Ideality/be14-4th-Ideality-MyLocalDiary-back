CREATE TRIGGER trg_comment_like_delete
    AFTER DELETE ON likes
    FOR EACH ROW
BEGIN
    IF OLD.type = 'COMMENT' THEN
        UPDATE comment
        SET likes_count = likes_count - 1
        WHERE id = OLD.target_id;
    END IF;
END;
