ALTER TABLE public.post
    ADD COLUMN IF NOT EXISTS title_en varchar,
    ADD COLUMN IF NOT EXISTS title_ua varchar,
    ADD COLUMN IF NOT EXISTS content_en text,
    ADD COLUMN IF NOT EXISTS content_ua text,
    ADD COLUMN IF NOT EXISTS created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE public.post
SET
    title_en = title,
    content_en = content
WHERE title IS NOT NULL OR content IS NOT NULL;

ALTER TABLE public.post
    DROP COLUMN title,
    DROP COLUMN content;