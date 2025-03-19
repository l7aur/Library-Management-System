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
6a858d39-3a3e-42aa-9b93-888ffd477717	laur	grad	Laur1!	1	eu
99d1737b-4812-4ddc-93a6-05539f8aee4b	test	test	test1A!	2	test
\.


--
-- Data for Name: author; Type: TABLE DATA; Schema: public; Owner: simple_user
--

COPY public.author (id, alias, first_name, last_name, nationality) FROM stdin;
dddf3daf-f06a-4fcd-8a21-838e8891b58b		Leo	Tolstoy	Russian
c03a5feb-afe4-4eaa-b8a2-a5034c767066		Marin	Sorescu	Romanian
7979dfb2-6076-460d-bd0f-95f4703c3897		Michael	Nielsen	Australian-American
219d05a8-cc22-41bc-9ea4-ba69db68b6d9	Ike	Isaac	Chuang	American
2da1beba-730c-4de9-bc07-47f0cac8b5ad		Winfield	Hill	American
53c0e4fc-ccac-4cf9-a01a-cb1aaf18b450	alias3	Michael	Johnson	Canadian
f4d9f432-2150-4cd1-bd36-bbff5e9c7b61	alias6	Mary	Wilson	Canadian
c2f7983c-eae9-4ff5-a6ea-7ad9f651b04b	alias8	Sarah	Taylor	Australian
b6ac1d63-7ec3-48d1-bd92-cb639f6e5f45	alias9	Robert	Anderson	American
72d0f8f4-c2a5-4b63-b2ba-ec7b10c295df	alias10	Linda	Thomas	Canadian
1dce3e1d-8ab5-4efb-ae4c-d85b365222a4	alias11	William	Jackson	British
6f76e6e7-bd5a-46e5-ae7e-48eddd46c5a1	alias12	Patricia	White	American
6f706872-54b7-4a82-91bb-1cf2c65855bb	alias13	Joseph	Harris	Canadian
aefcde53-bc27-44fa-b8c9-b7c819ce3250	alias14	Margaret	Martin	Australian
ed25b262-7069-4b2f-b41b-39645310cfe5	alias17	Thomas	Young	Canadian
6190c9a9-2b2e-44f1-b1c5-c65ed539980a	alias19	Steven	Wright	British
\.


--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: simple_user
--

COPY public.book (id, isbn, publish_year, title, publisher_id, price, stock) FROM stdin;
7530f627-7dbb-4245-b21d-7b39b2822575	1234567890123	2000	test	82a2921f-2150-4f08-98ba-26f5ae6e528f	200.5	10
8815ae78-60bf-48d6-a901-f1a410424590	1234567890124	2002	test2	82a2921f-2150-4f08-98ba-26f5ae6e528f	100.5	100
3cca3e28-ce46-4f2b-a862-ea4723efd890	1234567890154	2004	test4	82a2921f-2150-4f08-98ba-26f5ae6e528f	100.5	100
7fb964c4-a8cc-40d6-a9dd-834c39fafb60	1234562890154	2004	test5	82a2921f-2150-4f08-98ba-26f5ae6e528f	100.5	100
af71d073-0e6b-419b-87e7-88f7464dbb05	1234562890114	2004	test6	82a2921f-2150-4f08-98ba-26f5ae6e528f	100.5	100
964b68c8-9436-4026-bb4a-e58c34fa7ba6	1234512890114	2004	test7	82a2921f-2150-4f08-98ba-26f5ae6e528f	100.5	100
07fbc52f-9087-41ea-8f87-a9dc565cff54	1234502890114	2004	test8	82a2921f-2150-4f08-98ba-26f5ae6e528f	100.5	100
11391929-5034-4d04-9adc-001633a89863	9234502890114	2004	test9	82a2921f-2150-4f08-98ba-26f5ae6e528f	100.5	100
6366d5b2-1492-4c7d-b8d3-50441f49ed7c	9234902890114	2005	test10	82a2921f-2150-4f08-98ba-26f5ae6e528f	100.5	100
89c72ddf-b720-4c1f-8f54-4b54ff9bc0bb	9999999999999	2000	webtest	60ace1b8-ea53-486f-8b03-47caa03dd0cd	200.5	10
2a7172a6-b144-4e28-b5d1-8c1de3ce486a	0987654321098	2001	pe mine ma cheama riciu test	60ececbc-f86c-4ae5-9b3b-8e83a760fd9d	10000.75	1
019f956e-0d59-4a4f-9799-a247ab647140	abcdefghijklm	2023	Quantum Computation and Quantum Information	aeea3c1b-5ae8-454b-bbd3-2bcfbd96c5c3	69.99	10
\.


--
-- Data for Name: book_author; Type: TABLE DATA; Schema: public; Owner: simple_user
--

COPY public.book_author (book_id, author_id) FROM stdin;
7530f627-7dbb-4245-b21d-7b39b2822575	dddf3daf-f06a-4fcd-8a21-838e8891b58b
8815ae78-60bf-48d6-a901-f1a410424590	dddf3daf-f06a-4fcd-8a21-838e8891b58b
3cca3e28-ce46-4f2b-a862-ea4723efd890	dddf3daf-f06a-4fcd-8a21-838e8891b58b
7fb964c4-a8cc-40d6-a9dd-834c39fafb60	dddf3daf-f06a-4fcd-8a21-838e8891b58b
af71d073-0e6b-419b-87e7-88f7464dbb05	dddf3daf-f06a-4fcd-8a21-838e8891b58b
964b68c8-9436-4026-bb4a-e58c34fa7ba6	dddf3daf-f06a-4fcd-8a21-838e8891b58b
07fbc52f-9087-41ea-8f87-a9dc565cff54	dddf3daf-f06a-4fcd-8a21-838e8891b58b
11391929-5034-4d04-9adc-001633a89863	dddf3daf-f06a-4fcd-8a21-838e8891b58b
6366d5b2-1492-4c7d-b8d3-50441f49ed7c	dddf3daf-f06a-4fcd-8a21-838e8891b58b
89c72ddf-b720-4c1f-8f54-4b54ff9bc0bb	dddf3daf-f06a-4fcd-8a21-838e8891b58b
89c72ddf-b720-4c1f-8f54-4b54ff9bc0bb	c03a5feb-afe4-4eaa-b8a2-a5034c767066
2a7172a6-b144-4e28-b5d1-8c1de3ce486a	dddf3daf-f06a-4fcd-8a21-838e8891b58b
019f956e-0d59-4a4f-9799-a247ab647140	7979dfb2-6076-460d-bd0f-95f4703c3897
019f956e-0d59-4a4f-9799-a247ab647140	219d05a8-cc22-41bc-9ea4-ba69db68b6d9
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
60ececbc-f86c-4ae5-9b3b-8e83a760fd9d	1913	Hoboken, New Jersey, USA	Prentice Hall
ee08a5ca-887d-4083-9948-4fbe78bb6314	1826	Paris, France	Hachette Livre
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

