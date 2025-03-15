--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: app_user; Type: TABLE; Schema: public; Owner: simple_user
--

CREATE TABLE public.app_user (
    id uuid NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255) NOT NULL,
    role smallint NOT NULL,
    username character varying(255) NOT NULL,
    CONSTRAINT app_user_role_check CHECK (((role >= 0) AND (role <= 3)))
);


ALTER TABLE public.app_user OWNER TO simple_user;

--
-- Name: author; Type: TABLE; Schema: public; Owner: simple_user
--

CREATE TABLE public.author (
    id uuid NOT NULL,
    alias character varying(255),
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    nationality character varying(255)
);


ALTER TABLE public.author OWNER TO simple_user;

--
-- Name: book; Type: TABLE; Schema: public; Owner: simple_user
--

CREATE TABLE public.book (
    id uuid NOT NULL,
    isbn character varying(255) NOT NULL,
    publish_year integer,
    title character varying(255) NOT NULL,
    publisher_id uuid NOT NULL,
    price double precision NOT NULL,
    stock integer NOT NULL
);


ALTER TABLE public.book OWNER TO simple_user;

--
-- Name: book_author; Type: TABLE; Schema: public; Owner: simple_user
--

CREATE TABLE public.book_author (
    book_id uuid NOT NULL,
    author_id uuid NOT NULL
);


ALTER TABLE public.book_author OWNER TO simple_user;

--
-- Name: publisher; Type: TABLE; Schema: public; Owner: simple_user
--

CREATE TABLE public.publisher (
    id uuid NOT NULL,
    founding_year integer,
    location character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.publisher OWNER TO simple_user;

--
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: simple_user
--

COPY public.app_user (id, first_name, last_name, password, role, username) FROM stdin;
\.


--
-- Data for Name: author; Type: TABLE DATA; Schema: public; Owner: simple_user
--

COPY public.author (id, alias, first_name, last_name, nationality) FROM stdin;
dddf3daf-f06a-4fcd-8a21-838e8891b58b		Leo	Tolstoy	Russian
c03a5feb-afe4-4eaa-b8a2-a5034c767066		Marin	Sorescu	Romanian
deeaccf9-f90f-4b4f-9e9e-d1c8071b5a9d		Gabriel	Liiceanu	Romanian
1e4af9f3-0a4d-4907-8eb7-c44a84e435a2	Bardul de la Mircesti	Vasile	Alecsandri	Romanian
341fc564-5337-421e-b50e-1df85996bd0f	Stendhal	Marie-Henri	Beyle	French
177f7c86-60f3-402c-af71-2189d4981862		Honore	de Balzac	French
f2f8a192-0441-4ceb-8031-c6ad8433535f		Emile Edouard Charles Antoine	Zola	French
54f3e8ed-ebb7-4093-bbc6-349d005548b4		Jane	Austen	British
f0b3b4ac-a7c8-4d77-84a2-049db14cdfcd		Olga	Tokarczuk	Polish
a5d7c364-5fd4-43a1-a029-593515d5a424		Alice	Munro	Canadian
158bce9b-f494-4a21-ba57-d89a7d7f17ab		Hortensia	Papadat-Bengescu	Romanian
f85ed25c-3a61-4975-ba0f-fcb806268086		Agatha	Christie	British
3ab80854-4343-4d26-aeee-26fba2a7ae27		Simone Lucie Ernestine Marie Bertrand	de Beauvoir	French
c33ae86e-221a-4b4d-aba2-0dd74de09c14		Albert	Camus	French
9ba6b52a-4e18-4543-b446-9da0a95b1459		Martin	Heidegger	German
00fd9741-a0ba-42f8-abe8-c8fc2388de0e		Umberto	Eco	Italian
44a45119-f2b0-4dc5-a3ed-53be05ff5e3b		Jose de Sousa	Saramago	Portuguese
1e6f556d-cc86-4b6a-8f32-2d7358cc0d84		Gabriel	Garcia Marques	Colombian
0a78f982-fbc1-4395-9c35-05d0440e3f22	Uncle Bob	Robert Cecil	Martin	American
a1d236e1-041f-4b3a-bcd8-e11fe158fc88		Robert	Sapolsky	American
7979dfb2-6076-460d-bd0f-95f4703c3897		Michael	Nielsen	Australian-American
219d05a8-cc22-41bc-9ea4-ba69db68b6d9	Ike	Isaac	Chuang	American
5bae7b10-2547-498a-a707-ef80e6b6bf64		Paul	Horowitz	American
2da1beba-730c-4de9-bc07-47f0cac8b5ad		Winfield	Hill	American
8326d237-adc5-4a3c-8507-7d34210069e9		Bjarne	Stroustrup	Danish
\.


--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: simple_user
--

COPY public.book (id, isbn, publish_year, title, publisher_id, price, stock) FROM stdin;
\.


--
-- Data for Name: book_author; Type: TABLE DATA; Schema: public; Owner: simple_user
--

COPY public.book_author (book_id, author_id) FROM stdin;
\.


--
-- Data for Name: publisher; Type: TABLE DATA; Schema: public; Owner: simple_user
--

COPY public.publisher (id, founding_year, location, name) FROM stdin;
6662661c-f553-4bb8-8eb1-f52e7e158de8	1927	New York, USA	Penguin Random House
e451ff12-7905-4349-8dbf-907ec058f2a2	1991	Bucharest, Romania	Humanitas
7e7997cf-dd6f-439d-bc89-9e5d7cd8ce1b	1993	Bucharest, Romania	RAO
f0a27aa5-978a-4580-8a46-379f6a0d7c65	1993	Bucharest, Romania	Nemira
b52a599f-ad90-4b68-a7c8-a5c58c6635b2	2004	Bucharest, Romania	Univers
c872f422-8a3d-4f5a-8602-4c1ad338e887	2000	Cluj-Napoca, Romania	Polirom
aeea3c1b-5ae8-454b-bbd3-2bcfbd96c5c3	1534	Cambridge	Cambridge University Press
5c989f43-ac32-4d85-aba8-1c7cb9095ae1	1942	Boston	Addison-Wesley
60ace1b8-ea53-486f-8b03-47caa03dd0cd	1903	Boca Raton	CRC Press
7aa3b4c0-dab7-45fd-a965-b12350843268	2000	Cluj-Napoca	U.T. Press
82a2921f-2150-4f08-98ba-26f5ae6e528f	1989	Bucuresti	Litera
a521c7dd-7f29-4a33-97b4-168a53c8cdcf	1998	London	Pearson
\.


--
-- Name: app_user app_user_pkey; Type: CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);


--
-- Name: author author_pkey; Type: CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);


--
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- Name: publisher publisher_pkey; Type: CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.publisher
    ADD CONSTRAINT publisher_pkey PRIMARY KEY (id);


--
-- Name: app_user uk3k4cplvh82srueuttfkwnylq0; Type: CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT uk3k4cplvh82srueuttfkwnylq0 UNIQUE (username);


--
-- Name: book ukehpdfjpu1jm3hijhj4mm0hx9h; Type: CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT ukehpdfjpu1jm3hijhj4mm0hx9h UNIQUE (isbn);


--
-- Name: publisher ukh9trv4xhmh6s68vbw9ba6to70; Type: CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.publisher
    ADD CONSTRAINT ukh9trv4xhmh6s68vbw9ba6to70 UNIQUE (name);


--
-- Name: book_author fkbjqhp85wjv8vpr0beygh6jsgo; Type: FK CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.book_author
    ADD CONSTRAINT fkbjqhp85wjv8vpr0beygh6jsgo FOREIGN KEY (author_id) REFERENCES public.author(id);


--
-- Name: book fkgtvt7p649s4x80y6f4842pnfq; Type: FK CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fkgtvt7p649s4x80y6f4842pnfq FOREIGN KEY (publisher_id) REFERENCES public.publisher(id);


--
-- Name: book_author fkhwgu59n9o80xv75plf9ggj7xn; Type: FK CONSTRAINT; Schema: public; Owner: simple_user
--

ALTER TABLE ONLY public.book_author
    ADD CONSTRAINT fkhwgu59n9o80xv75plf9ggj7xn FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- PostgreSQL database dump complete
--

