-- Messages --
CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    message TEXT NOT NULL,
    status VARCHAR(20) CHECK (status IN ('unread', 'read', 'replied', 'archived')) NOT NULL DEFAULT 'unread',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Index on status for faster filtering
CREATE INDEX idx_messages_status ON messages(status);

-- Index on created_at for sorting by date
CREATE INDEX idx_messages_created_at ON messages(created_at DESC);

-- Trigger to auto-update updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_messages_updated_at BEFORE UPDATE ON messages
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();




-- Softwares --
CREATE TABLE softwares (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status VARCHAR(20) CHECK (status IN ('published', 'draft', 'archived')) NOT NULL DEFAULT 'draft',
    company_name VARCHAR(150) NOT NULL,
    location VARCHAR(200),
    logo VARCHAR(500),
    bar_color VARCHAR(7),
    title VARCHAR(255) NOT NULL,
    about_client TEXT,
    deliverables VARCHAR(255),
    about_img VARCHAR(500),
    challenge TEXT,
    solution TEXT,
    stack VARCHAR(255),
    showcase_imgs TEXT[],
    showcase_bg_color VARCHAR(7),
    achievements TEXT[],
    mockup_img VARCHAR(500),
    mockup_bg_img VARCHAR(500),
    software_description TEXT
);

-- Index on status for faster filtering
CREATE INDEX idx_softwares_status ON softwares(status);




-- Brands --
CREATE TABLE brands (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status VARCHAR(20) CHECK (status IN ('published', 'draft', 'archived')) NOT NULL DEFAULT 'draft',
    display_img VARCHAR(500),
    company_name VARCHAR(150) NOT NULL,
    title VARCHAR(255) NOT NULL,
    bg_color VARCHAR(7),
    location VARCHAR(200),
    hero_img VARCHAR(500),
    about_client TEXT,
    goal TEXT,
    showcase_imgs TEXT[],
    showcase_bg_color VARCHAR(7)
);

-- Index on status for faster filtering
CREATE INDEX idx_brands_status ON brands(status);



-- Testimonials --
CREATE TABLE testimonials (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL ,
    company VARCHAR(100) NOT NULL ,
    testimony TEXT NOT NULL,
    profile_pic VARCHAR(255) NOT NULL
);


-- Blogs --
CREATE TABLE blogs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status VARCHAR(20) CHECK (status IN ('publish', 'draft', 'archived')) NOT NULL DEFAULT 'draft',
    title VARCHAR(255) NOT NULL,
    hero_img VARCHAR(500),
    body TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Optional: Create an index on status for faster filtering
CREATE INDEX idx_blogs_status ON blogs(status);

-- Optional: Trigger to auto-update updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_blogs_updated_at BEFORE UPDATE ON blogs
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();


-- Jobs --
-- Create jobs table
CREATE TABLE jobs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status VARCHAR(20) CHECK (status IN ('open', 'closed', 'draft')) NOT NULL DEFAULT 'draft',
    location VARCHAR(200),
    type VARCHAR(20) CHECK (type IN ('full_time', 'part_time', 'onsite', 'remote')) NOT NULL,
    department VARCHAR(100),
    body TEXT NOT NULL,
    deadline DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create applicants table
CREATE TABLE applicants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status VARCHAR(20) CHECK (status IN ('new', 'viewed', 'shortlisted', 'rejected', 'hired')) NOT NULL DEFAULT 'new',
    job_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    resume VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_job
        FOREIGN KEY(job_id)
        REFERENCES jobs(id)
        ON DELETE CASCADE
);

-- Indexes for jobs
CREATE INDEX idx_jobs_status ON jobs(status);
CREATE INDEX idx_jobs_deadline ON jobs(deadline);

-- Indexes for applicants
CREATE INDEX idx_applicants_status ON applicants(status);
CREATE INDEX idx_applicants_job_id ON applicants(job_id);
CREATE INDEX idx_applicants_created_at ON applicants(created_at DESC);

-- Trigger function (create once if not exists)
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers for auto-update updated_at
CREATE TRIGGER update_jobs_updated_at BEFORE UPDATE ON jobs
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_applicants_updated_at BEFORE UPDATE ON applicants
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();