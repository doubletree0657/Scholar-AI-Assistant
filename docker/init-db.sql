-- Initialize the pgvector extension for vector similarity search
CREATE EXTENSION IF NOT EXISTS vector;

-- Create schema for better organization (optional, but recommended for production)
CREATE SCHEMA IF NOT EXISTS scholar_ai;

-- Grant privileges
GRANT ALL PRIVILEGES ON SCHEMA scholar_ai TO scholar_user;
GRANT ALL PRIVILEGES ON DATABASE scholar_ai_db TO scholar_user;

-- Set search path
ALTER DATABASE scholar_ai_db SET search_path TO scholar_ai, public;

-- Log successful initialization
DO $$
BEGIN
    RAISE NOTICE 'Scholar AI Database initialized successfully with pgvector extension';
END $$;
