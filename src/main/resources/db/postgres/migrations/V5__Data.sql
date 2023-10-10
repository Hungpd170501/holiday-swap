--
-- TOC entry 4740 (class 0 OID 18348)
-- Dependencies: 219
-- Data for Name: admin_wallet; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.admin_wallet
VALUES (2, 1.5);


--
-- TOC entry 4742 (class 0 OID 18354)
-- Dependencies: 221
-- Data for Name: all_transaction_log; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.all_transaction_log
VALUES (1, 2, 3, 5, 0, 'Success', '2023/10/02 10:55:50', 5, 14.5, 0.5);
INSERT INTO public.all_transaction_log
VALUES (2, 2, 3, 5, 0, 'Success', '2023/10/02 13:47:01', 5, 14.5, 0.5);
INSERT INTO public.all_transaction_log
VALUES (3, 2, 3, 5, 0, 'Success', '2023/10/02 13:47:04', 0, 19, 0.5);
INSERT INTO public.all_transaction_log
VALUES (4, 6, 2, 5, 0, 'Success', '2023/10/03 15:47:05', 495, 14.5, 0.5);


--
-- TOC entry 4801 (class 0 OID 18582)
-- Dependencies: 280
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--


--
-- TOC entry 4806 (class 0 OID 18602)
-- Dependencies: 285
-- Data for Name: property_type; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.property_type
VALUES (10,
        'a quad room is set up for four people to stay comfortably. This means the room will have two double beds. Some, however, may be set up dormitory-style with bunks or twins, so check with the property to make sure.',
        false, 'Quad room');
INSERT INTO public.property_type
VALUES (11, 'a room with a queen-sized bed. ', false, 'Queen');
INSERT INTO public.property_type
VALUES (12, 'a room with a king-sized bed.', false, 'King');
INSERT INTO public.property_type
VALUES (22,
        'aparthotels are offering these types of rooms, but they can also be found at other traditional hotel chains. These rooms target long stay guests with full kitchens, laundry, and other amenities that make it possible to live comfortably. Housekeeping services are limited to once or twice a week. ',
        false, 'Apartment-style');
INSERT INTO public.property_type
VALUES (23,
        'hotels are required by law to provide a certain number of handicapped-accessible rooms. These rooms will have space for a wheelchair to move easily, and a bathroom outfitted for a disabled person.',
        false, 'Accessible room');
INSERT INTO public.property_type
VALUES (24, 'cabana rooms open out onto the swimming pool or have a private pool', false, 'Cabana');
INSERT INTO public.property_type
VALUES (25,
        'most villas can be found at resorts. These kinds of rooms are actually stand-alone houses that have extra space and privacy. Villas typically come equipped with multiple bedrooms, a living room, a swimming pool, and a balcony.',
        false, 'Villa');
INSERT INTO public.property_type
VALUES (26,
        'not all hotels offer penthouse suites, but these rooms are high-end, big rooms – sometimes taking up the entire top floor of a hotel – and come with the ultimate luxury amenities.',
        false, 'Penthouse');
INSERT INTO public.property_type
VALUES (27, 'Penthouse', false, 'null');
INSERT INTO public.property_type
VALUES (1,
        'these rooms are assigned to one person or a couple. It may have one or more beds, but the size of the bed depends on the hotel. Some single rooms have a twin bed, most will have a double, few will have a queen bed.',
        false, 'Single room');
INSERT INTO public.property_type
VALUES (8, 'double rooms are assigned to two people; expect one double bed, or two twin beds depending on the hotel.',
        false, 'Double room');
INSERT INTO public.property_type
VALUES (9,
        'as the name might suggest, this room is equipped for three people to stay. The room will have a combination of either three twin beds, one double bed and a twin, or two double beds.',
        false, 'Triple room');
INSERT INTO public.property_type
VALUES (13, 'a room with two twin-sized beds.', false, 'Twin');
INSERT INTO public.property_type
VALUES (14, 'Hollywood twin rooms have two twin beds that are joined by the same headboard.', false, 'Hollywood twin');
INSERT INTO public.property_type
VALUES (15,
        'these rooms have two double beds (sometimes two queen beds) and are meant to accommodate two to four people, especially families traveling with young kids.',
        false, 'Double-double');
INSERT INTO public.property_type
VALUES (16,
        'this type of room has a studio bed, e.g. a couch that can be converted into a bed. Some studios come with additional beds. Others come with more space: a studio room can be like a fully-furnished apartment, meaning it will have a small kitchenette. Check with the hotel to learn more about their studio rooms.',
        false, 'Studio');
INSERT INTO public.property_type
VALUES (17,
        'a standard room is likely the same as a queen or a single room, great for a solo traveler or a couple. Expect a double bed. ',
        false, 'Standard room');
INSERT INTO public.property_type
VALUES (18,
        'these rooms might be a bit bigger with slightly upgraded amenities or a nicer view. These rooms are typically equipped for groups who need more space, like a couple or small family. ',
        false, 'Deluxe room');
INSERT INTO public.property_type
VALUES (19,
        'a joint room, sometimes called an adjoining room, refers to two rooms that share a common wall but no connecting door. Joint rooms are meant for families with younger children who may be old enough to stay in their own space, but not too far from their parents.',
        false, 'Joint room');
INSERT INTO public.property_type
VALUES (20,
        'these rooms have a connecting door between them, as well as individual doors to get to the outside. Great for families or groups who don’t want to have to walk through the hallway to move between rooms.',
        false, 'Connecting room');
INSERT INTO public.property_type
VALUES (21,
        'suites come in a few different sizes. A basic suite or executive suite comes with a separate living space connected to one or more bedrooms. This set up is sometimes also called a master suite. A mini-suite or junior suite refers to a single room with a bed and sitting area. Some suites also come with kitchenettes. The presidential suite, as the name would suggest, is usually the most expensive room provided by a hotel. It will have one or more bedrooms, a living space, and impressive amenities, decoration, and tailor-made services. ',
        false, 'Suite');


--
-- TOC entry 4808 (class 0 OID 18611)
-- Dependencies: 287
-- Data for Name: property_view; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.property_view
VALUES (1, 'Ocean view', 'A view of the ocean from the property.', false);
INSERT INTO public.property_view
VALUES (2, 'Beachfront view	', 'A view of the beach from the property.', false);
INSERT INTO public.property_view
VALUES (3, 'Pool view', 'A view of the pool from the property.', false);
INSERT INTO public.property_view
VALUES (4, 'Garden view', 'A view of the garden from the property.', false);
INSERT INTO public.property_view
VALUES (5, 'Mountain view', 'A view of the mountains from the property.', false);
INSERT INTO public.property_view
VALUES (6, 'City view', 'A view of the city from the property.', false);
INSERT INTO public.property_view
VALUES (7, 'Balcony view	', 'A view from the balcony of the property.', false);
INSERT INTO public.property_view
VALUES (8, 'Terrace view', 'A view from the terrace of the property.', false);
INSERT INTO public.property_view
VALUES (9, 'Patio view', 'A view from the patio of the property.', false);
INSERT INTO public.property_view
VALUES (10, 'Club lounge view	', 'A view of the club lounge from the property.', false);
INSERT INTO public.property_view
VALUES (11, 'Spa view', 'A view of the spa from the property.', false);
INSERT INTO public.property_view
VALUES (12, 'Golf course view	', 'A view of the golf course from the property.', false);


--
-- TOC entry 4768 (class 0 OID 18465)
-- Dependencies: 247
-- Data for Name: resort; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.resort
VALUES (1, 'Best Western Premier Sonasea Phu Quoc', NULL, false,
        'When you stay at Best Western Premier Sonasea Phu Quoc in Phu Quoc, you''ll be on the beach and steps from Walking Street Phu Quoc. This 5-star aparthotel is 7.4 mi (11.8 km) from Phu Quoc Night Market and 5 mi (8.1 km) from Phu Quoc Beach.',
        'ACTIVE');
INSERT INTO public.resort
VALUES (2, 'Vinpearl Resort & Spa Phu Quoc', NULL, false,
        'Welcoming guests with its bright red tiles, Indochine-style architecture, and an outdoor swimming pool of nearly 5,000m2, Vinpearl Resort & Spa Phu Quoc offers a fun-filled journey with typical natural flavors of the pearl island. Must-try experiences here include savoring a seafood buffet with magnificent sunset views at Pepper Restaurant by the sea, enjoying a unique Balinese massage at spa huts above the lake, and conquering thousands of entertainment experiences at VinWonders right nearby.',
        'ACTIVE');
INSERT INTO public.resort
VALUES (3, 'Seashells Phu Quoc Hotel & Spa', NULL, false, 'A stay at Seashells Hotel and Spa Phu Quoc places you in the heart of Phu Quoc, within a 5-minute walk of Dinh Ba Thuy-Long Thanh-Mau and Dinh Cau. This 5-star hotel is 0.2 mi (0.3 km) from Phu Quoc Night Market and 2.5 mi (4.1 km) from Phu Quoc Beach.
Pamper yourself with a visit to the spa, which offers massages. You can take advantage of recreational amenities such as an outdoor pool and a fitness center. Additional amenities at this hotel include complimentary wireless Internet access, concierge services, and barbecue grills.
Enjoy a meal at the restaurant or snacks in the coffee shop/cafe. The hotel also offers 24-hour room service. Relax with your favorite drink at the bar/lounge or the poolside bar.
Featured amenities include complimentary newspapers in the lobby, dry cleaning/laundry services, and a 24-hour front desk. A roundtrip airport shuttle is complimentary during limited hours.
Make yourself at home in one of the 252 air-conditioned rooms featuring refrigerators and minibars. Rooms have private balconies. LED televisions with cable programming provide entertainment, while complimentary wireless Internet access keeps you connected. Private bathrooms with separate bathtubs and showers feature complimentary toiletries and hair dryers.',
        'ACTIVE');
INSERT INTO public.resort
VALUES (4, 'Premier Village Phu Quoc Resort Managed by AccorHotels', NULL, false, 'Located in Phu Quoc, Premier Village Phu Quoc Resort Managed by AccorHotels is within a 15-minute drive of Phu Quoc Prison and An Thoi Cable Car Station. This 5-star resort is 16.8 mi (27.1 km) from Phu Quoc Night Market and 4.9 mi (7.9 km) from Sao Beach.
Pamper yourself with a visit to the spa, which offers massages, body treatments, and facials. After a day at the private beach, you can enjoy other recreational amenities including an outdoor pool and a spa tub. This resort also features complimentary wireless Internet access, concierge services, and babysitting (surcharge).
Enjoy international cuisine at The Market, one of the resort''s 2 restaurants, or stay in and take advantage of the 24-hour room service. Relax with a refreshing drink from the poolside bar or one of the 3 bars/lounges. A complimentary continental breakfast is served daily from 6 AM to 10:30 AM.
Featured amenities include a business center, express check-in, and complimentary newspapers in the lobby. A roundtrip airport shuttle is complimentary (available 24 hours).
Treat yourself to a stay in one of the 126 guestrooms, featuring private pools and flat-screen televisions. Your bed comes with down comforters and Egyptian cotton sheets. Rooms have private balconies. Kitchens are outfitted with full-sized refrigerators/freezers, stovetops, and espresso makers. Complimentary wireless Internet access is available to keep you connected. Bathrooms with separate bathtubs and showers feature deep soaking bathtubs and rainfall showerheads.',
        'ACTIVE');
INSERT INTO public.resort
VALUES (5, 'Wyndham Garden Grandworld Phu Quoc', NULL, false, 'With a stay at Wyndham Garden Grandworld Phu Quoc in Phu Quoc, you''ll be steps from Shophouse Grand World and a 5-minute drive from Vinpearl Golf Phu Quoc. This hotel is 1.5 mi (2.4 km) from Corona Casino and 2.4 mi (3.8 km) from VinWonders Phu Quoc.
Make yourself at home in one of the 921 guestrooms.', 'ACTIVE');


--
-- TOC entry 4804 (class 0 OID 18593)
-- Dependencies: 283
-- Data for Name: property; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--
INSERT INTO public.property
(property_id,
 number_king_beds,
 number_queen_beds,
 number_twin_beds,
 number_full_beds,
 number_sofa_beds,
 number_murphy_beds,
 number_beds_room,
 number_baths_room,
 room_size,
 is_deleted,
 status,
 property_type_id,
 property_view_id,
 property_description,
 property_name,
 number_single_beds,
 number_double_beds,
 resort_id)
VALUES (14, 1, 1, 0, 0, 0, 0, 3, 1, 350, false, 'ACTIVE', 25, 1, '4 beds', 'Sunset Villa with Private Pool', 2, 0, 4),
       (1, 1, 0, 0, 0, 0, 0, 1, 1, 37, false, 'ACTIVE', 12, 1, '1 king bed(2m wide)', 'Deluxe King Room', 0, 0, 1),
       (2, 1, 0, 0, 0, 0, 0, 3, 1, 212, false, 'ACTIVE', 25, 2, '4 single beds(1m wide) and 1 king bed(2m wide)',
        'Deluxe Villa', 4, 0, 1),
       (3, 1, 1, 0, 0, 0, 0, 3, 1, 116, false, 'ACTIVE', 21, 2,
        '2 single beds(1m wide) and 1 king bed(2m wide) and 1 queen bed(1.6m wide)', 'Grande Suite', 2, 0, 1),
       (4, 1, 1, 0, 0, 0, 0, 3, 1, 116, false, 'ACTIVE', 21, 2,
        '1 king bed(2m wide) and 1 queen bed(1.6m wide) and 2 single beds(1.1m wide)', 'Executive Three Bedroom Suite',
        2, 0, 1),
       (5, 3, 0, 0, 0, 0, 0, 0, 1, 340, false, 'ACTIVE', 25, 1, '3 king beds(1.81m wide)',
        'Three Bedroom Villa Lake View - Private Pool', 0, 0, 2),
       (6, 0, 0, 0, 0, 0, 0, 0, 1, 46, false, 'ACTIVE', 18, 1, '2 single beds(1.2m wide)',
        'Deluxe Garden View Twin Bed', 2, 0, 2),
       (7, 2, 0, 0, 0, 0, 0, 2, 1, 65, false, 'ACTIVE', 21, 1, '2 king beds', 'Phu Quoc Two Bedroom Suite', 0, 0, 3),
       (8, 0, 2, 0, 0, 0, 0, 2, 1, 66, false, 'ACTIVE', 21, 1, '1 queen bed and 2 single beds',
        'Family Ocean View Two Bedroom', 2, 0, 3),
       (9, 0, 2, 0, 0, 0, 0, 2, 1, 73, false, 'ACTIVE', 21, 1, '2 queen beds(1.6m wide)', 'Executive Suite', 0, 0, 1),
       (10, 1, 0, 0, 0, 0, 0, 1, 1, 50, false, 'ACTIVE', 21, 1, '1 king bed', 'Classic City View King Room', 0, 0, 3),
       (11, 1, 1, 0, 0, 0, 0, 3, 1, 622, false, 'ACTIVE', 25, 1,
        '1 king bed(2m wide) and 1 queen bed(1.8m wide) and 2 single beds(1m wide)', 'Rock Villa With Private Pool', 2,
        0, 3),
       (12, 1, 1, 0, 0, 0, 0, 3, 1, 350, false, 'ACTIVE', 25, 1,
        '1 king bed(1.81m-3m wide) and 1 queen bed(1.51m-1.8m wide) and 1 single bed(0.8m-1.3m wide)',
        '3 Bedroom Garden Villa With Private Pool', 1, 0, 4),
       (13, 1, 1, 0, 0, 0, 0, 3, 1, 340, false, 'ACTIVE', 25, 1,
        '1 king bed(2m wide) and 1 queen bed(1.8m wide) and 2 single beds(1m wide)', 'Hillside Villa With Private Pool',
        2, 0, 4);


--
-- TOC entry 4795 (class 0 OID 18558)
-- Dependencies: 274
-- Data for Name: wallet; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.wallet
VALUES (2, 10, true);
INSERT INTO public.wallet
VALUES (3, 495, true);
INSERT INTO public.wallet
VALUES (1, 14.5, true);


--
-- TOC entry 4791 (class 0 OID 18541)
-- Dependencies: 270
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--
INSERT INTO public.users
VALUES (5, NULL, NULL, NULL, NULL, 'kienptse150397@fpt.edu.vn',
        '$2a$12$8EL3keQj4MmaxsshALJFHe1YAh/fFi97/UPaUSQ0B7Wq9A7BwJg4i', 'kienpt', 'Pham Thanh Kien', NULL, 'MALE',
        '2001-06-06', '0766831327', true, true, 'ACTIVE', NULL, 1);
INSERT INTO public.users
VALUES (6, '2023-10-02 17:05:14.998647', 'anonymousUser', '2023-10-03 15:45:32.544949', 'anonymousUser',
        'tinmcit@gmail.com', '$2a$10$FGCzQC//7.enKvUtJWdx0OXLcdtXkmXajUmmnHSzH0dCZ1XHoI3jC', 'tinit28', NULL, NULL,
        'FEMALE', '2023-10-02', '0858797595', true, false, 'ACTIVE', 3, 1);
INSERT INTO public.users
VALUES (8, '2023-10-07 19:09:16.94421', 'anonymousUser', '2023-10-08 13:14:51.774257', 'anonymousUser',
        'tin2@gmail.com', '$2a$10$KJyOqK0pHfZ6/5DFiH.Auu479IPfcufsgpbh2DV8yWRt4cN6NgCeO', 'test@gmail.com', NULL, NULL,
        'MALE', '2023-10-07', '0858797595', false, false, 'ACTIVE', NULL, 1);


--
-- TOC entry 4756 (class 0 OID 18418)
-- Dependencies: 235
-- Data for Name: co_owner; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 1, 5, '001');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 2, 5, '001');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 3, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 4, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 5, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 6, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 7, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 8, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 9, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 10, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 11, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 12, 5, '002');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 13, 5, '001');
INSERT INTO public.co_owner
VALUES (NULL, NULL, 'DEEDED', 'PENDING', false, 14, 5, '001');

--
-- TOC entry 4746 (class 0 OID 18371)
-- Dependencies: 225
-- Data for Name: conversation; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.conversation
VALUES (2, '2023-10-02 11:44:51.622568', 'hungpd7', '2023-10-02 11:44:51.622588', 'hungpd7');
INSERT INTO public.conversation
VALUES (3, '2023-10-02 12:57:02.329763', 'hungpd7', '2023-10-02 12:57:02.329793', 'hungpd7');


--
-- TOC entry 4747 (class 0 OID 18378)
-- Dependencies: 226
-- Data for Name: conversation_participant; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.conversation_participant
VALUES ('2023-10-02 11:44:51.64301', 'hungpd7', '2023-10-02 11:44:51.643049', 'hungpd7', false, 2, 3);
INSERT INTO public.conversation_participant
VALUES ('2023-10-02 12:57:02.342363', 'hungpd7', '2023-10-02 12:57:02.342391', 'hungpd7', false, 3, 3);


--
-- TOC entry 4749 (class 0 OID 18387)
-- Dependencies: 228
-- Data for Name: cron_job_log; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

--
-- TOC entry 4799 (class 0 OID 18573)
-- Dependencies: 278
-- Data for Name: in_room_amenity_type; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.in_room_amenity_type
VALUES (1, 'Toiletries', 'Toiletries', false);
INSERT INTO public.in_room_amenity_type
VALUES (2, 'Room Layout and Furnishings', 'Room Layout and Furnishings', false);
INSERT INTO public.in_room_amenity_type
VALUES (3, 'Internet and Communication Devices', 'Internet and Communication Devices', false);
INSERT INTO public.in_room_amenity_type
VALUES (4, 'Kitchen amenities', 'Kitchen amenities', false);
INSERT INTO public.in_room_amenity_type
VALUES (5, 'Food & Drinks', 'Food & Drinks', false);
INSERT INTO public.in_room_amenity_type
VALUES (6, 'Bathroom facilities', 'Bathroom facilities', false);
INSERT INTO public.in_room_amenity_type
VALUES (7, 'Room Amenities', 'Room Amenities', false);
INSERT INTO public.in_room_amenity_type
VALUES (8, 'Media/Technology', 'Media/Technology', false);
INSERT INTO public.in_room_amenity_type
VALUES (9, 'Amenities', 'Amenities', false);
INSERT INTO public.in_room_amenity_type
VALUES (10, 'Cleaning Services', 'Cleaning Services', false);
INSERT INTO public.in_room_amenity_type
VALUES (11, 'Outdoor View', 'Outdoor View', false);


--
-- TOC entry 4797 (class 0 OID 18564)
-- Dependencies: 276
-- Data for Name: in_room_amenity; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.in_room_amenity
VALUES (1, 'Toothbrush', 'Toothbrush', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/87fe984b-0c2c-401e-a930-069400528a89_Toothbrush.png');
INSERT INTO public.in_room_amenity
VALUES (2, 'Toothpaste', 'Toothpaste', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/072a4447-a188-4245-9dc2-4d6ba40914d7_Toothpaste.png');
INSERT INTO public.in_room_amenity
VALUES (3, 'Body Wash', 'Body Wash', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/50771df3-c585-4838-ac26-1ae2519d6e5f_Body%20Wash.png');
INSERT INTO public.in_room_amenity
VALUES (4, 'Shampoo', 'Shampoo', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/5ef9e680-0e1e-4dd2-b104-407a8c33ece4_Shampoo.png');
INSERT INTO public.in_room_amenity
VALUES (5, 'Sofa', 'Sofa', false, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/29233fd7-abdc-4f67-854c-97cb51b1fcf0_Sofa.png');
INSERT INTO public.in_room_amenity
VALUES (6, 'Desk', 'Desk', false, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/d4eba5ef-be28-471d-ba95-e1e535a4c901_Desk.png');
INSERT INTO public.in_room_amenity
VALUES (7, 'Private Entrance', 'Private Entrance', false, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/d67f25e2-1ee8-40dc-aad7-373bd30b7ba3_Private%20Entrance.png');
INSERT INTO public.in_room_amenity
VALUES (8, 'Balcony', 'Balcony', false, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e5a0ccd2-97e0-4b24-98c1-ed8cb8682c6d_Balcony.png');
INSERT INTO public.in_room_amenity
VALUES (9, 'Wi-Fi', 'Wi-Fi', false, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/3d1cb577-cb61-4e3b-b709-bf3881235d10_Wi-Fi.png');
INSERT INTO public.in_room_amenity
VALUES (10, 'Telephone', 'Telephone', false, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e0d5547d-5218-4b42-b581-55f3d6844147_Telephone.png');
INSERT INTO public.in_room_amenity
VALUES (11, 'International Long-Distance Calls', 'International Long-Distance Calls', false, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/cf0acf9e-1f19-40b7-a55b-89543aaee6b5_International%20Long-Distance%20Calls.png');
INSERT INTO public.in_room_amenity
VALUES (12, 'Microwave Oven', 'Microwave Oven', false, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/000163cd-fda6-4b31-b159-effd1378deb0_Microwave%20Oven.png');
INSERT INTO public.in_room_amenity
VALUES (13, 'Refrigerator', 'Refrigerator', false, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/dc4fe2cf-dc8a-47f5-b214-762e91968912_Refrigerator.png');
INSERT INTO public.in_room_amenity
VALUES (14, 'Coffee/Tea Pot', 'Coffee/Tea Pot', false, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/ae1a160e-65ed-4182-b613-6a3319d775ee_Coffee.png');
INSERT INTO public.in_room_amenity
VALUES (15, 'Bottled Water', 'Bottled Water', false, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7c03ff90-30df-4430-9dd7-066de395206a_Bottled%20Water.png');
INSERT INTO public.in_room_amenity
VALUES (16, 'Minibar', 'Minibar', false, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8b69c544-43e9-4fe0-ba13-a3c33a55b1bd_Minibar.png');
INSERT INTO public.in_room_amenity
VALUES (17, 'Private Bathroom', 'Private Bathroom', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/95b6e005-fa3a-4955-9269-7dae0428dc61_Private%20Bathroom.png');
INSERT INTO public.in_room_amenity
VALUES (18, 'Private Toilet', 'Private Toilet', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e2342715-5fe9-408a-b936-31aada609e6c_Private%20Toilet.png');
INSERT INTO public.in_room_amenity
VALUES (19, 'Hair Dryer', 'Hair Dryer', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/67513b2d-41aa-4f94-ba87-5d3ca4322d6a_Hair%20Dryer.png');
INSERT INTO public.in_room_amenity
VALUES (20, 'Bathroom Makeup Mirror', 'Bathroom Makeup Mirror', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/18b707d2-f7a4-4218-b9b3-47aaf0481cc0_Bathroom%20Makeup%20Mirror.png');
INSERT INTO public.in_room_amenity
VALUES (21, 'Bathrobe', 'Bathrobe', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1bbfdd1f-3c12-4c5a-ac25-74006cb53bdb_Bathrobe.png');
INSERT INTO public.in_room_amenity
VALUES (22, '24-Hour Hot Water', '24-Hour Hot Water', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/cefbc913-684c-4fcd-8aca-e77f94d4540d_Slippers.png');
INSERT INTO public.in_room_amenity
VALUES (23, 'Slippers', 'Slippers', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/cefbc913-684c-4fcd-8aca-e77f94d4540d_Slippers.png');
INSERT INTO public.in_room_amenity
VALUES (24, 'Air Conditioning', 'Air Conditioning', false, 7,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1161f0ae-24f6-4d1e-bc0a-9a8ae1a34b6f_Air%20Conditioning.png');
INSERT INTO public.in_room_amenity
VALUES (25, 'Automatic Curtain', 'Automatic Curtain', false, 7,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/5a08dc0e-6521-46c6-86ed-5e8c1d20b44b_Automatic%20Curtain.png');
INSERT INTO public.in_room_amenity
VALUES (26, 'Shade Curtain', 'Shade Curtain', false, 7,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/736f2dcb-f30e-4cb6-95be-14c6dc136c55_Shade%20Curtain.png');
INSERT INTO public.in_room_amenity
VALUES (27, 'Bedding: Duvet', 'Bedding: Duvet', false, 7,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9cf0ce75-1c3c-4388-828d-8602e863adb8_BeddingDuvet.png');
INSERT INTO public.in_room_amenity
VALUES (28, 'Bedding: Blanket Or Quilt', 'Bedding: Blanket Or Quilt', false, 7,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/aa59e005-15ee-4cea-bffb-b181b0e4b48d_BeddingDuvet.png');
INSERT INTO public.in_room_amenity
VALUES (29, 'TV', 'TV', false, 8,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/980d3331-88a4-45b7-a02a-083b4d81cf53_TV.png');
INSERT INTO public.in_room_amenity
VALUES (30, 'Cable Channels', 'Cable Channels', false, 8,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8b145074-bbce-4baa-ab43-ff44ff286a95_Cable%20Channels.png');
INSERT INTO public.in_room_amenity
VALUES (31, 'Audio Equipment', 'Audio Equipment', false, 8,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/230d5b99-1231-445e-b0a7-27b0545a6cae_Audio%20Equipment.png');
INSERT INTO public.in_room_amenity
VALUES (32, 'Butler Service', 'Butler Service', false, 8,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8cda9978-e7ad-4bb8-933b-39b441b12d08_Butler%20Service.png');
INSERT INTO public.in_room_amenity
VALUES (33, 'In-Room Safe', 'In-Room Safe', false, 9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b69c4cad-9600-4a3a-91c5-3caf1133e5f7_Shade%20Curtain.png');
INSERT INTO public.in_room_amenity
VALUES (34, 'Sewing Kit', 'Sewing Kit', false, 9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/db61d350-efe3-4778-8a8a-fad3ef5563dd_Sewing%20Kit.png');
INSERT INTO public.in_room_amenity
VALUES (35, 'Turn Down Service', 'Turn Down Service', false, 9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/a0754cd3-67ec-4d86-a476-28863380cbd6_Turndown%20service.png');
INSERT INTO public.in_room_amenity
VALUES (36, '220V Socket', '220V Socket', false, 9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/59a7e044-f67f-4d25-853c-e4bb298f9b39_220V%20Socket.png');
INSERT INTO public.in_room_amenity
VALUES (37, 'Umbrella', 'Umbrella', false, 9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/470a9a31-e112-4fa7-bc68-c401d659ad7a_Umbrella.png');
INSERT INTO public.in_room_amenity
VALUES (38, 'Ironing Equipment', 'Ironing Equipment', false, 10,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/79968b61-0dfc-41d0-82da-e53c3f16f8d7_Ironing%20Equipment.png');


--
-- TOC entry 4751 (class 0 OID 18395)
-- Dependencies: 230
-- Data for Name: message; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--


--
-- TOC entry 4753 (class 0 OID 18403)
-- Dependencies: 232
-- Data for Name: money_transfer; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.money_transfer
VALUES (2, 2, NULL, 'test01', 10000, 10, '2023/10/02 10:50:52', 1);
INSERT INTO public.money_transfer
VALUES (1, 3, NULL, 'test 01', 10000, 10, '2023/10/02 10:37:18', 2);
INSERT INTO public.money_transfer
VALUES (3, 3, NULL, 'test01', 10000, 10, '2023/10/02 10:52:32', 1);
INSERT INTO public.money_transfer
VALUES (4, 3, NULL, 'test 02', 10000, 10, '2023/10/02 18:12:26', 0);
INSERT INTO public.money_transfer
VALUES (5, 2, NULL, 'test', 300000, 300, '2023/10/02 21:29:26', 2);
INSERT INTO public.money_transfer
VALUES (7, 6, NULL, 'test', 100000, 100, '2023/10/03 15:36:31', 2);
INSERT INTO public.money_transfer
VALUES (8, 6, NULL, 'test', 500000, 500, '2023/10/03 15:39:05', 2);
INSERT INTO public.money_transfer
VALUES (9, 6, NULL, 'test', 500000, 500, '2023/10/03 15:44:10', 1);
INSERT INTO public.money_transfer
VALUES (6, 2, NULL, 'nap_tien_vnp', 230000, 230, '2023/10/02 21:31:38', 2);
INSERT INTO public.money_transfer
VALUES (10, 2, NULL, 'nap_tien_vnp', 100000, 100, '2023/10/05 13:18:07', 0);


--
-- TOC entry 4755 (class 0 OID 18411)
-- Dependencies: 234
-- Data for Name: notification; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--


--
-- TOC entry 4758 (class 0 OID 18427)
-- Dependencies: 237
-- Data for Name: payment_methods; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--


--
-- TOC entry 4760 (class 0 OID 18433)
-- Dependencies: 239
-- Data for Name: plan; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.plan
VALUES (1, 'Membership 1', 'description', NULL, 10, 'RECURRING', 'MONTHLY', '2023-10-02 11:34:56.781128',
        '2023-10-02 11:34:56.781213', true);


--
-- TOC entry 4762 (class 0 OID 18441)
-- Dependencies: 241
-- Data for Name: point; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.point
VALUES (1, 1000, '2023/10/02 03:36:59', 0);


--
-- TOC entry 4802 (class 0 OID 18589)
-- Dependencies: 281
-- Data for Name: properties_amenities; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.properties_amenities
VALUES (1, 1);
INSERT INTO public.properties_amenities
VALUES (2, 1);
INSERT INTO public.properties_amenities
VALUES (3, 1);
INSERT INTO public.properties_amenities
VALUES (4, 1);
INSERT INTO public.properties_amenities
VALUES (5, 1);
INSERT INTO public.properties_amenities
VALUES (6, 1);
INSERT INTO public.properties_amenities
VALUES (7, 1);
INSERT INTO public.properties_amenities
VALUES (8, 1);
INSERT INTO public.properties_amenities
VALUES (9, 1);
INSERT INTO public.properties_amenities
VALUES (10, 1);
INSERT INTO public.properties_amenities
VALUES (11, 1);
INSERT INTO public.properties_amenities
VALUES (12, 1);
INSERT INTO public.properties_amenities
VALUES (13, 1);
INSERT INTO public.properties_amenities
VALUES (14, 1);
INSERT INTO public.properties_amenities
VALUES (15, 1);
INSERT INTO public.properties_amenities
VALUES (16, 1);
INSERT INTO public.properties_amenities
VALUES (17, 1);
INSERT INTO public.properties_amenities
VALUES (18, 1);
INSERT INTO public.properties_amenities
VALUES (19, 1);
INSERT INTO public.properties_amenities
VALUES (20, 1);
INSERT INTO public.properties_amenities
VALUES (21, 1);
INSERT INTO public.properties_amenities
VALUES (22, 1);
INSERT INTO public.properties_amenities
VALUES (23, 1);
INSERT INTO public.properties_amenities
VALUES (24, 1);
INSERT INTO public.properties_amenities
VALUES (25, 1);
INSERT INTO public.properties_amenities
VALUES (26, 1);
INSERT INTO public.properties_amenities
VALUES (27, 1);
INSERT INTO public.properties_amenities
VALUES (28, 1);
INSERT INTO public.properties_amenities
VALUES (29, 1);
INSERT INTO public.properties_amenities
VALUES (30, 1);
INSERT INTO public.properties_amenities
VALUES (31, 1);
INSERT INTO public.properties_amenities
VALUES (32, 1);
INSERT INTO public.properties_amenities
VALUES (33, 1);
INSERT INTO public.properties_amenities
VALUES (34, 1);
INSERT INTO public.properties_amenities
VALUES (35, 1);
INSERT INTO public.properties_amenities
VALUES (36, 1);
INSERT INTO public.properties_amenities
VALUES (37, 1);
INSERT INTO public.properties_amenities
VALUES (38, 1);
INSERT INTO public.properties_amenities
VALUES (1, 2);
INSERT INTO public.properties_amenities
VALUES (2, 2);
INSERT INTO public.properties_amenities
VALUES (3, 2);
INSERT INTO public.properties_amenities
VALUES (4, 2);
INSERT INTO public.properties_amenities
VALUES (5, 2);
INSERT INTO public.properties_amenities
VALUES (6, 2);
INSERT INTO public.properties_amenities
VALUES (7, 2);
INSERT INTO public.properties_amenities
VALUES (8, 2);
INSERT INTO public.properties_amenities
VALUES (9, 2);
INSERT INTO public.properties_amenities
VALUES (10, 2);
INSERT INTO public.properties_amenities
VALUES (11, 2);
INSERT INTO public.properties_amenities
VALUES (12, 2);
INSERT INTO public.properties_amenities
VALUES (13, 2);
INSERT INTO public.properties_amenities
VALUES (14, 2);
INSERT INTO public.properties_amenities
VALUES (15, 2);
INSERT INTO public.properties_amenities
VALUES (16, 2);
INSERT INTO public.properties_amenities
VALUES (17, 2);
INSERT INTO public.properties_amenities
VALUES (18, 2);
INSERT INTO public.properties_amenities
VALUES (19, 2);
INSERT INTO public.properties_amenities
VALUES (20, 2);
INSERT INTO public.properties_amenities
VALUES (21, 2);
INSERT INTO public.properties_amenities
VALUES (22, 2);
INSERT INTO public.properties_amenities
VALUES (23, 2);
INSERT INTO public.properties_amenities
VALUES (24, 2);
INSERT INTO public.properties_amenities
VALUES (25, 2);
INSERT INTO public.properties_amenities
VALUES (26, 2);
INSERT INTO public.properties_amenities
VALUES (27, 2);
INSERT INTO public.properties_amenities
VALUES (28, 2);
INSERT INTO public.properties_amenities
VALUES (29, 2);
INSERT INTO public.properties_amenities
VALUES (30, 2);
INSERT INTO public.properties_amenities
VALUES (31, 2);
INSERT INTO public.properties_amenities
VALUES (32, 2);
INSERT INTO public.properties_amenities
VALUES (33, 2);
INSERT INTO public.properties_amenities
VALUES (34, 2);
INSERT INTO public.properties_amenities
VALUES (35, 2);
INSERT INTO public.properties_amenities
VALUES (36, 2);
INSERT INTO public.properties_amenities
VALUES (37, 2);
INSERT INTO public.properties_amenities
VALUES (38, 2);
INSERT INTO public.properties_amenities
VALUES (1, 3);
INSERT INTO public.properties_amenities
VALUES (2, 3);
INSERT INTO public.properties_amenities
VALUES (3, 3);
INSERT INTO public.properties_amenities
VALUES (4, 3);
INSERT INTO public.properties_amenities
VALUES (5, 3);
INSERT INTO public.properties_amenities
VALUES (6, 3);
INSERT INTO public.properties_amenities
VALUES (7, 3);
INSERT INTO public.properties_amenities
VALUES (8, 3);
INSERT INTO public.properties_amenities
VALUES (9, 3);
INSERT INTO public.properties_amenities
VALUES (10, 3);
INSERT INTO public.properties_amenities
VALUES (11, 3);
INSERT INTO public.properties_amenities
VALUES (12, 3);
INSERT INTO public.properties_amenities
VALUES (13, 3);
INSERT INTO public.properties_amenities
VALUES (14, 3);
INSERT INTO public.properties_amenities
VALUES (15, 3);
INSERT INTO public.properties_amenities
VALUES (16, 3);
INSERT INTO public.properties_amenities
VALUES (17, 3);
INSERT INTO public.properties_amenities
VALUES (18, 3);
INSERT INTO public.properties_amenities
VALUES (19, 3);
INSERT INTO public.properties_amenities
VALUES (20, 3);
INSERT INTO public.properties_amenities
VALUES (21, 3);
INSERT INTO public.properties_amenities
VALUES (22, 3);
INSERT INTO public.properties_amenities
VALUES (23, 3);
INSERT INTO public.properties_amenities
VALUES (24, 3);
INSERT INTO public.properties_amenities
VALUES (25, 3);
INSERT INTO public.properties_amenities
VALUES (26, 3);
INSERT INTO public.properties_amenities
VALUES (27, 3);
INSERT INTO public.properties_amenities
VALUES (28, 3);
INSERT INTO public.properties_amenities
VALUES (29, 3);
INSERT INTO public.properties_amenities
VALUES (30, 3);
INSERT INTO public.properties_amenities
VALUES (31, 3);
INSERT INTO public.properties_amenities
VALUES (32, 3);
INSERT INTO public.properties_amenities
VALUES (33, 3);
INSERT INTO public.properties_amenities
VALUES (34, 3);
INSERT INTO public.properties_amenities
VALUES (35, 3);
INSERT INTO public.properties_amenities
VALUES (36, 3);
INSERT INTO public.properties_amenities
VALUES (37, 3);
INSERT INTO public.properties_amenities
VALUES (38, 3);
INSERT INTO public.properties_amenities
VALUES (1, 4);
INSERT INTO public.properties_amenities
VALUES (2, 4);
INSERT INTO public.properties_amenities
VALUES (3, 4);
INSERT INTO public.properties_amenities
VALUES (4, 4);
INSERT INTO public.properties_amenities
VALUES (5, 4);
INSERT INTO public.properties_amenities
VALUES (6, 4);
INSERT INTO public.properties_amenities
VALUES (7, 4);
INSERT INTO public.properties_amenities
VALUES (8, 4);
INSERT INTO public.properties_amenities
VALUES (9, 4);
INSERT INTO public.properties_amenities
VALUES (10, 4);
INSERT INTO public.properties_amenities
VALUES (11, 4);
INSERT INTO public.properties_amenities
VALUES (12, 4);
INSERT INTO public.properties_amenities
VALUES (13, 4);
INSERT INTO public.properties_amenities
VALUES (14, 4);
INSERT INTO public.properties_amenities
VALUES (15, 4);
INSERT INTO public.properties_amenities
VALUES (16, 4);
INSERT INTO public.properties_amenities
VALUES (17, 4);
INSERT INTO public.properties_amenities
VALUES (18, 4);
INSERT INTO public.properties_amenities
VALUES (19, 4);
INSERT INTO public.properties_amenities
VALUES (20, 4);
INSERT INTO public.properties_amenities
VALUES (21, 4);
INSERT INTO public.properties_amenities
VALUES (22, 4);
INSERT INTO public.properties_amenities
VALUES (23, 4);
INSERT INTO public.properties_amenities
VALUES (24, 4);
INSERT INTO public.properties_amenities
VALUES (25, 4);
INSERT INTO public.properties_amenities
VALUES (26, 4);
INSERT INTO public.properties_amenities
VALUES (27, 4);
INSERT INTO public.properties_amenities
VALUES (28, 4);
INSERT INTO public.properties_amenities
VALUES (29, 4);
INSERT INTO public.properties_amenities
VALUES (30, 4);
INSERT INTO public.properties_amenities
VALUES (31, 4);
INSERT INTO public.properties_amenities
VALUES (32, 4);
INSERT INTO public.properties_amenities
VALUES (33, 4);
INSERT INTO public.properties_amenities
VALUES (34, 4);
INSERT INTO public.properties_amenities
VALUES (35, 4);
INSERT INTO public.properties_amenities
VALUES (36, 4);
INSERT INTO public.properties_amenities
VALUES (37, 4);
INSERT INTO public.properties_amenities
VALUES (38, 4);
INSERT INTO public.properties_amenities
VALUES (1, 5);
INSERT INTO public.properties_amenities
VALUES (2, 5);
INSERT INTO public.properties_amenities
VALUES (3, 5);
INSERT INTO public.properties_amenities
VALUES (4, 5);
INSERT INTO public.properties_amenities
VALUES (5, 5);
INSERT INTO public.properties_amenities
VALUES (6, 5);
INSERT INTO public.properties_amenities
VALUES (7, 5);
INSERT INTO public.properties_amenities
VALUES (8, 5);
INSERT INTO public.properties_amenities
VALUES (9, 5);
INSERT INTO public.properties_amenities
VALUES (10, 5);
INSERT INTO public.properties_amenities
VALUES (11, 5);
INSERT INTO public.properties_amenities
VALUES (12, 5);
INSERT INTO public.properties_amenities
VALUES (13, 5);
INSERT INTO public.properties_amenities
VALUES (14, 5);
INSERT INTO public.properties_amenities
VALUES (15, 5);
INSERT INTO public.properties_amenities
VALUES (16, 5);
INSERT INTO public.properties_amenities
VALUES (17, 5);
INSERT INTO public.properties_amenities
VALUES (18, 5);
INSERT INTO public.properties_amenities
VALUES (19, 5);
INSERT INTO public.properties_amenities
VALUES (20, 5);
INSERT INTO public.properties_amenities
VALUES (21, 5);
INSERT INTO public.properties_amenities
VALUES (22, 5);
INSERT INTO public.properties_amenities
VALUES (23, 5);
INSERT INTO public.properties_amenities
VALUES (24, 5);
INSERT INTO public.properties_amenities
VALUES (25, 5);
INSERT INTO public.properties_amenities
VALUES (26, 5);
INSERT INTO public.properties_amenities
VALUES (27, 5);
INSERT INTO public.properties_amenities
VALUES (28, 5);
INSERT INTO public.properties_amenities
VALUES (29, 5);
INSERT INTO public.properties_amenities
VALUES (30, 5);
INSERT INTO public.properties_amenities
VALUES (31, 5);
INSERT INTO public.properties_amenities
VALUES (32, 5);
INSERT INTO public.properties_amenities
VALUES (33, 5);
INSERT INTO public.properties_amenities
VALUES (34, 5);
INSERT INTO public.properties_amenities
VALUES (35, 5);
INSERT INTO public.properties_amenities
VALUES (36, 5);
INSERT INTO public.properties_amenities
VALUES (37, 5);
INSERT INTO public.properties_amenities
VALUES (38, 5);
INSERT INTO public.properties_amenities
VALUES (1, 6);
INSERT INTO public.properties_amenities
VALUES (2, 6);
INSERT INTO public.properties_amenities
VALUES (3, 6);
INSERT INTO public.properties_amenities
VALUES (4, 6);
INSERT INTO public.properties_amenities
VALUES (5, 6);
INSERT INTO public.properties_amenities
VALUES (6, 6);
INSERT INTO public.properties_amenities
VALUES (7, 6);
INSERT INTO public.properties_amenities
VALUES (8, 6);
INSERT INTO public.properties_amenities
VALUES (9, 6);
INSERT INTO public.properties_amenities
VALUES (10, 6);
INSERT INTO public.properties_amenities
VALUES (11, 6);
INSERT INTO public.properties_amenities
VALUES (12, 6);
INSERT INTO public.properties_amenities
VALUES (13, 6);
INSERT INTO public.properties_amenities
VALUES (14, 6);
INSERT INTO public.properties_amenities
VALUES (15, 6);
INSERT INTO public.properties_amenities
VALUES (16, 6);
INSERT INTO public.properties_amenities
VALUES (17, 6);
INSERT INTO public.properties_amenities
VALUES (18, 6);
INSERT INTO public.properties_amenities
VALUES (19, 6);
INSERT INTO public.properties_amenities
VALUES (20, 6);
INSERT INTO public.properties_amenities
VALUES (21, 6);
INSERT INTO public.properties_amenities
VALUES (22, 6);
INSERT INTO public.properties_amenities
VALUES (23, 6);
INSERT INTO public.properties_amenities
VALUES (24, 6);
INSERT INTO public.properties_amenities
VALUES (25, 6);
INSERT INTO public.properties_amenities
VALUES (26, 6);
INSERT INTO public.properties_amenities
VALUES (27, 6);
INSERT INTO public.properties_amenities
VALUES (28, 6);
INSERT INTO public.properties_amenities
VALUES (29, 6);
INSERT INTO public.properties_amenities
VALUES (30, 6);
INSERT INTO public.properties_amenities
VALUES (31, 6);
INSERT INTO public.properties_amenities
VALUES (32, 6);
INSERT INTO public.properties_amenities
VALUES (33, 6);
INSERT INTO public.properties_amenities
VALUES (34, 6);
INSERT INTO public.properties_amenities
VALUES (35, 6);
INSERT INTO public.properties_amenities
VALUES (36, 6);
INSERT INTO public.properties_amenities
VALUES (37, 6);
INSERT INTO public.properties_amenities
VALUES (38, 6);
INSERT INTO public.properties_amenities
VALUES (1, 7);
INSERT INTO public.properties_amenities
VALUES (2, 7);
INSERT INTO public.properties_amenities
VALUES (3, 7);
INSERT INTO public.properties_amenities
VALUES (4, 7);
INSERT INTO public.properties_amenities
VALUES (5, 7);
INSERT INTO public.properties_amenities
VALUES (6, 7);
INSERT INTO public.properties_amenities
VALUES (7, 7);
INSERT INTO public.properties_amenities
VALUES (8, 7);
INSERT INTO public.properties_amenities
VALUES (9, 7);
INSERT INTO public.properties_amenities
VALUES (10, 7);
INSERT INTO public.properties_amenities
VALUES (11, 7);
INSERT INTO public.properties_amenities
VALUES (12, 7);
INSERT INTO public.properties_amenities
VALUES (13, 7);
INSERT INTO public.properties_amenities
VALUES (14, 7);
INSERT INTO public.properties_amenities
VALUES (15, 7);
INSERT INTO public.properties_amenities
VALUES (16, 7);
INSERT INTO public.properties_amenities
VALUES (17, 7);
INSERT INTO public.properties_amenities
VALUES (18, 7);
INSERT INTO public.properties_amenities
VALUES (19, 7);
INSERT INTO public.properties_amenities
VALUES (20, 7);
INSERT INTO public.properties_amenities
VALUES (21, 7);
INSERT INTO public.properties_amenities
VALUES (22, 7);
INSERT INTO public.properties_amenities
VALUES (23, 7);
INSERT INTO public.properties_amenities
VALUES (24, 7);
INSERT INTO public.properties_amenities
VALUES (25, 7);
INSERT INTO public.properties_amenities
VALUES (26, 7);
INSERT INTO public.properties_amenities
VALUES (27, 7);
INSERT INTO public.properties_amenities
VALUES (28, 7);
INSERT INTO public.properties_amenities
VALUES (29, 7);
INSERT INTO public.properties_amenities
VALUES (30, 7);
INSERT INTO public.properties_amenities
VALUES (31, 7);
INSERT INTO public.properties_amenities
VALUES (32, 7);
INSERT INTO public.properties_amenities
VALUES (33, 7);
INSERT INTO public.properties_amenities
VALUES (34, 7);
INSERT INTO public.properties_amenities
VALUES (35, 7);
INSERT INTO public.properties_amenities
VALUES (36, 7);
INSERT INTO public.properties_amenities
VALUES (37, 7);
INSERT INTO public.properties_amenities
VALUES (38, 7);
INSERT INTO public.properties_amenities
VALUES (1, 8);
INSERT INTO public.properties_amenities
VALUES (2, 8);
INSERT INTO public.properties_amenities
VALUES (3, 8);
INSERT INTO public.properties_amenities
VALUES (4, 8);
INSERT INTO public.properties_amenities
VALUES (5, 8);
INSERT INTO public.properties_amenities
VALUES (6, 8);
INSERT INTO public.properties_amenities
VALUES (7, 8);
INSERT INTO public.properties_amenities
VALUES (8, 8);
INSERT INTO public.properties_amenities
VALUES (9, 8);
INSERT INTO public.properties_amenities
VALUES (10, 8);
INSERT INTO public.properties_amenities
VALUES (11, 8);
INSERT INTO public.properties_amenities
VALUES (12, 8);
INSERT INTO public.properties_amenities
VALUES (13, 8);
INSERT INTO public.properties_amenities
VALUES (14, 8);
INSERT INTO public.properties_amenities
VALUES (15, 8);
INSERT INTO public.properties_amenities
VALUES (16, 8);
INSERT INTO public.properties_amenities
VALUES (17, 8);
INSERT INTO public.properties_amenities
VALUES (18, 8);
INSERT INTO public.properties_amenities
VALUES (19, 8);
INSERT INTO public.properties_amenities
VALUES (20, 8);
INSERT INTO public.properties_amenities
VALUES (21, 8);
INSERT INTO public.properties_amenities
VALUES (22, 8);
INSERT INTO public.properties_amenities
VALUES (23, 8);
INSERT INTO public.properties_amenities
VALUES (24, 8);
INSERT INTO public.properties_amenities
VALUES (25, 8);
INSERT INTO public.properties_amenities
VALUES (26, 8);
INSERT INTO public.properties_amenities
VALUES (27, 8);
INSERT INTO public.properties_amenities
VALUES (28, 8);
INSERT INTO public.properties_amenities
VALUES (29, 8);
INSERT INTO public.properties_amenities
VALUES (30, 8);
INSERT INTO public.properties_amenities
VALUES (31, 8);
INSERT INTO public.properties_amenities
VALUES (32, 8);
INSERT INTO public.properties_amenities
VALUES (33, 8);
INSERT INTO public.properties_amenities
VALUES (34, 8);
INSERT INTO public.properties_amenities
VALUES (35, 8);
INSERT INTO public.properties_amenities
VALUES (36, 8);
INSERT INTO public.properties_amenities
VALUES (37, 8);
INSERT INTO public.properties_amenities
VALUES (38, 8);
INSERT INTO public.properties_amenities
VALUES (1, 9);
INSERT INTO public.properties_amenities
VALUES (2, 9);
INSERT INTO public.properties_amenities
VALUES (3, 9);
INSERT INTO public.properties_amenities
VALUES (4, 9);
INSERT INTO public.properties_amenities
VALUES (5, 9);
INSERT INTO public.properties_amenities
VALUES (6, 9);
INSERT INTO public.properties_amenities
VALUES (7, 9);
INSERT INTO public.properties_amenities
VALUES (8, 9);
INSERT INTO public.properties_amenities
VALUES (9, 9);
INSERT INTO public.properties_amenities
VALUES (10, 9);
INSERT INTO public.properties_amenities
VALUES (11, 9);
INSERT INTO public.properties_amenities
VALUES (12, 9);
INSERT INTO public.properties_amenities
VALUES (13, 9);
INSERT INTO public.properties_amenities
VALUES (14, 9);
INSERT INTO public.properties_amenities
VALUES (15, 9);
INSERT INTO public.properties_amenities
VALUES (16, 9);
INSERT INTO public.properties_amenities
VALUES (17, 9);
INSERT INTO public.properties_amenities
VALUES (18, 9);
INSERT INTO public.properties_amenities
VALUES (19, 9);
INSERT INTO public.properties_amenities
VALUES (20, 9);
INSERT INTO public.properties_amenities
VALUES (21, 9);
INSERT INTO public.properties_amenities
VALUES (22, 9);
INSERT INTO public.properties_amenities
VALUES (23, 9);
INSERT INTO public.properties_amenities
VALUES (24, 9);
INSERT INTO public.properties_amenities
VALUES (25, 9);
INSERT INTO public.properties_amenities
VALUES (26, 9);
INSERT INTO public.properties_amenities
VALUES (27, 9);
INSERT INTO public.properties_amenities
VALUES (28, 9);
INSERT INTO public.properties_amenities
VALUES (29, 9);
INSERT INTO public.properties_amenities
VALUES (30, 9);
INSERT INTO public.properties_amenities
VALUES (31, 9);
INSERT INTO public.properties_amenities
VALUES (32, 9);
INSERT INTO public.properties_amenities
VALUES (33, 9);
INSERT INTO public.properties_amenities
VALUES (34, 9);
INSERT INTO public.properties_amenities
VALUES (35, 9);
INSERT INTO public.properties_amenities
VALUES (36, 9);
INSERT INTO public.properties_amenities
VALUES (37, 9);
INSERT INTO public.properties_amenities
VALUES (38, 9);
INSERT INTO public.properties_amenities
VALUES (1, 10);
INSERT INTO public.properties_amenities
VALUES (2, 10);
INSERT INTO public.properties_amenities
VALUES (3, 10);
INSERT INTO public.properties_amenities
VALUES (4, 10);
INSERT INTO public.properties_amenities
VALUES (5, 10);
INSERT INTO public.properties_amenities
VALUES (6, 10);
INSERT INTO public.properties_amenities
VALUES (7, 10);
INSERT INTO public.properties_amenities
VALUES (8, 10);
INSERT INTO public.properties_amenities
VALUES (9, 10);
INSERT INTO public.properties_amenities
VALUES (10, 10);
INSERT INTO public.properties_amenities
VALUES (11, 10);
INSERT INTO public.properties_amenities
VALUES (12, 10);
INSERT INTO public.properties_amenities
VALUES (13, 10);
INSERT INTO public.properties_amenities
VALUES (14, 10);
INSERT INTO public.properties_amenities
VALUES (15, 10);
INSERT INTO public.properties_amenities
VALUES (16, 10);
INSERT INTO public.properties_amenities
VALUES (17, 10);
INSERT INTO public.properties_amenities
VALUES (18, 10);
INSERT INTO public.properties_amenities
VALUES (19, 10);
INSERT INTO public.properties_amenities
VALUES (20, 10);
INSERT INTO public.properties_amenities
VALUES (21, 10);
INSERT INTO public.properties_amenities
VALUES (22, 10);
INSERT INTO public.properties_amenities
VALUES (23, 10);
INSERT INTO public.properties_amenities
VALUES (24, 10);
INSERT INTO public.properties_amenities
VALUES (25, 10);
INSERT INTO public.properties_amenities
VALUES (26, 10);
INSERT INTO public.properties_amenities
VALUES (27, 10);
INSERT INTO public.properties_amenities
VALUES (28, 10);
INSERT INTO public.properties_amenities
VALUES (29, 10);
INSERT INTO public.properties_amenities
VALUES (30, 10);
INSERT INTO public.properties_amenities
VALUES (31, 10);
INSERT INTO public.properties_amenities
VALUES (32, 10);
INSERT INTO public.properties_amenities
VALUES (33, 10);
INSERT INTO public.properties_amenities
VALUES (34, 10);
INSERT INTO public.properties_amenities
VALUES (35, 10);
INSERT INTO public.properties_amenities
VALUES (36, 10);
INSERT INTO public.properties_amenities
VALUES (37, 10);
INSERT INTO public.properties_amenities
VALUES (38, 10);
INSERT INTO public.properties_amenities
VALUES (1, 11);
INSERT INTO public.properties_amenities
VALUES (2, 11);
INSERT INTO public.properties_amenities
VALUES (3, 11);
INSERT INTO public.properties_amenities
VALUES (4, 11);
INSERT INTO public.properties_amenities
VALUES (5, 11);
INSERT INTO public.properties_amenities
VALUES (6, 11);
INSERT INTO public.properties_amenities
VALUES (7, 11);
INSERT INTO public.properties_amenities
VALUES (8, 11);
INSERT INTO public.properties_amenities
VALUES (9, 11);
INSERT INTO public.properties_amenities
VALUES (10, 11);
INSERT INTO public.properties_amenities
VALUES (11, 11);
INSERT INTO public.properties_amenities
VALUES (12, 11);
INSERT INTO public.properties_amenities
VALUES (13, 11);
INSERT INTO public.properties_amenities
VALUES (14, 11);
INSERT INTO public.properties_amenities
VALUES (15, 11);
INSERT INTO public.properties_amenities
VALUES (16, 11);
INSERT INTO public.properties_amenities
VALUES (17, 11);
INSERT INTO public.properties_amenities
VALUES (18, 11);
INSERT INTO public.properties_amenities
VALUES (19, 11);
INSERT INTO public.properties_amenities
VALUES (20, 11);
INSERT INTO public.properties_amenities
VALUES (21, 11);
INSERT INTO public.properties_amenities
VALUES (22, 11);
INSERT INTO public.properties_amenities
VALUES (23, 11);
INSERT INTO public.properties_amenities
VALUES (24, 11);
INSERT INTO public.properties_amenities
VALUES (25, 11);
INSERT INTO public.properties_amenities
VALUES (26, 11);
INSERT INTO public.properties_amenities
VALUES (27, 11);
INSERT INTO public.properties_amenities
VALUES (28, 11);
INSERT INTO public.properties_amenities
VALUES (29, 11);
INSERT INTO public.properties_amenities
VALUES (30, 11);
INSERT INTO public.properties_amenities
VALUES (31, 11);
INSERT INTO public.properties_amenities
VALUES (32, 11);
INSERT INTO public.properties_amenities
VALUES (33, 11);
INSERT INTO public.properties_amenities
VALUES (34, 11);
INSERT INTO public.properties_amenities
VALUES (35, 11);
INSERT INTO public.properties_amenities
VALUES (36, 11);
INSERT INTO public.properties_amenities
VALUES (37, 11);
INSERT INTO public.properties_amenities
VALUES (38, 11);
INSERT INTO public.properties_amenities
VALUES (1, 12);
INSERT INTO public.properties_amenities
VALUES (2, 12);
INSERT INTO public.properties_amenities
VALUES (3, 12);
INSERT INTO public.properties_amenities
VALUES (4, 12);
INSERT INTO public.properties_amenities
VALUES (5, 12);
INSERT INTO public.properties_amenities
VALUES (6, 12);
INSERT INTO public.properties_amenities
VALUES (7, 12);
INSERT INTO public.properties_amenities
VALUES (8, 12);
INSERT INTO public.properties_amenities
VALUES (9, 12);
INSERT INTO public.properties_amenities
VALUES (10, 12);
INSERT INTO public.properties_amenities
VALUES (11, 12);
INSERT INTO public.properties_amenities
VALUES (12, 12);
INSERT INTO public.properties_amenities
VALUES (13, 12);
INSERT INTO public.properties_amenities
VALUES (14, 12);
INSERT INTO public.properties_amenities
VALUES (15, 12);
INSERT INTO public.properties_amenities
VALUES (16, 12);
INSERT INTO public.properties_amenities
VALUES (17, 12);
INSERT INTO public.properties_amenities
VALUES (18, 12);
INSERT INTO public.properties_amenities
VALUES (19, 12);
INSERT INTO public.properties_amenities
VALUES (20, 12);
INSERT INTO public.properties_amenities
VALUES (21, 12);
INSERT INTO public.properties_amenities
VALUES (22, 12);
INSERT INTO public.properties_amenities
VALUES (23, 12);
INSERT INTO public.properties_amenities
VALUES (24, 12);
INSERT INTO public.properties_amenities
VALUES (25, 12);
INSERT INTO public.properties_amenities
VALUES (26, 12);
INSERT INTO public.properties_amenities
VALUES (27, 12);
INSERT INTO public.properties_amenities
VALUES (28, 12);
INSERT INTO public.properties_amenities
VALUES (29, 12);
INSERT INTO public.properties_amenities
VALUES (30, 12);
INSERT INTO public.properties_amenities
VALUES (31, 12);
INSERT INTO public.properties_amenities
VALUES (32, 12);
INSERT INTO public.properties_amenities
VALUES (33, 12);
INSERT INTO public.properties_amenities
VALUES (34, 12);
INSERT INTO public.properties_amenities
VALUES (35, 12);
INSERT INTO public.properties_amenities
VALUES (36, 12);
INSERT INTO public.properties_amenities
VALUES (37, 12);
INSERT INTO public.properties_amenities
VALUES (38, 12);
INSERT INTO public.properties_amenities
VALUES (1, 13);
INSERT INTO public.properties_amenities
VALUES (2, 13);
INSERT INTO public.properties_amenities
VALUES (3, 13);
INSERT INTO public.properties_amenities
VALUES (4, 13);
INSERT INTO public.properties_amenities
VALUES (5, 13);
INSERT INTO public.properties_amenities
VALUES (6, 13);
INSERT INTO public.properties_amenities
VALUES (7, 13);
INSERT INTO public.properties_amenities
VALUES (8, 13);
INSERT INTO public.properties_amenities
VALUES (9, 13);
INSERT INTO public.properties_amenities
VALUES (10, 13);
INSERT INTO public.properties_amenities
VALUES (11, 13);
INSERT INTO public.properties_amenities
VALUES (12, 13);
INSERT INTO public.properties_amenities
VALUES (13, 13);
INSERT INTO public.properties_amenities
VALUES (14, 13);
INSERT INTO public.properties_amenities
VALUES (15, 13);
INSERT INTO public.properties_amenities
VALUES (16, 13);
INSERT INTO public.properties_amenities
VALUES (17, 13);
INSERT INTO public.properties_amenities
VALUES (18, 13);
INSERT INTO public.properties_amenities
VALUES (19, 13);
INSERT INTO public.properties_amenities
VALUES (20, 13);
INSERT INTO public.properties_amenities
VALUES (21, 13);
INSERT INTO public.properties_amenities
VALUES (22, 13);
INSERT INTO public.properties_amenities
VALUES (23, 13);
INSERT INTO public.properties_amenities
VALUES (24, 13);
INSERT INTO public.properties_amenities
VALUES (25, 13);
INSERT INTO public.properties_amenities
VALUES (26, 13);
INSERT INTO public.properties_amenities
VALUES (27, 13);
INSERT INTO public.properties_amenities
VALUES (28, 13);
INSERT INTO public.properties_amenities
VALUES (29, 13);
INSERT INTO public.properties_amenities
VALUES (30, 13);
INSERT INTO public.properties_amenities
VALUES (31, 13);
INSERT INTO public.properties_amenities
VALUES (32, 13);
INSERT INTO public.properties_amenities
VALUES (33, 13);
INSERT INTO public.properties_amenities
VALUES (34, 13);
INSERT INTO public.properties_amenities
VALUES (35, 13);
INSERT INTO public.properties_amenities
VALUES (36, 13);
INSERT INTO public.properties_amenities
VALUES (37, 13);
INSERT INTO public.properties_amenities
VALUES (38, 13);
INSERT INTO public.properties_amenities
VALUES (1, 14);
INSERT INTO public.properties_amenities
VALUES (2, 14);
INSERT INTO public.properties_amenities
VALUES (3, 14);
INSERT INTO public.properties_amenities
VALUES (4, 14);
INSERT INTO public.properties_amenities
VALUES (5, 14);
INSERT INTO public.properties_amenities
VALUES (6, 14);
INSERT INTO public.properties_amenities
VALUES (7, 14);
INSERT INTO public.properties_amenities
VALUES (8, 14);
INSERT INTO public.properties_amenities
VALUES (9, 14);
INSERT INTO public.properties_amenities
VALUES (10, 14);
INSERT INTO public.properties_amenities
VALUES (11, 14);
INSERT INTO public.properties_amenities
VALUES (12, 14);
INSERT INTO public.properties_amenities
VALUES (13, 14);
INSERT INTO public.properties_amenities
VALUES (14, 14);
INSERT INTO public.properties_amenities
VALUES (15, 14);
INSERT INTO public.properties_amenities
VALUES (16, 14);
INSERT INTO public.properties_amenities
VALUES (17, 14);
INSERT INTO public.properties_amenities
VALUES (18, 14);
INSERT INTO public.properties_amenities
VALUES (19, 14);
INSERT INTO public.properties_amenities
VALUES (20, 14);
INSERT INTO public.properties_amenities
VALUES (21, 14);
INSERT INTO public.properties_amenities
VALUES (22, 14);
INSERT INTO public.properties_amenities
VALUES (23, 14);
INSERT INTO public.properties_amenities
VALUES (24, 14);
INSERT INTO public.properties_amenities
VALUES (25, 14);
INSERT INTO public.properties_amenities
VALUES (26, 14);
INSERT INTO public.properties_amenities
VALUES (27, 14);
INSERT INTO public.properties_amenities
VALUES (28, 14);
INSERT INTO public.properties_amenities
VALUES (29, 14);
INSERT INTO public.properties_amenities
VALUES (30, 14);
INSERT INTO public.properties_amenities
VALUES (31, 14);
INSERT INTO public.properties_amenities
VALUES (32, 14);
INSERT INTO public.properties_amenities
VALUES (33, 14);
INSERT INTO public.properties_amenities
VALUES (34, 14);
INSERT INTO public.properties_amenities
VALUES (35, 14);
INSERT INTO public.properties_amenities
VALUES (36, 14);
INSERT INTO public.properties_amenities
VALUES (37, 14);
INSERT INTO public.properties_amenities
VALUES (38, 14);


--
-- TOC entry 4764 (class 0 OID 18447)
-- Dependencies: 243
-- Data for Name: property_image; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.property_image
VALUES (2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/cae059ff-5f17-454d-adaf-a45943ed2f8d_0205e120009atkpb324FF_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7bb3924e-0bc7-4e1b-84ab-57b3bb736323_220a12000000rptueE423_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/4c58175e-18b5-419c-80ac-c40d06c3eed0_220d13000000ui4zs052B_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f39b50c8-849b-4615-ac1f-16b00ff400c8_0221b120009kl7zh7D3EE_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/3a6109a0-656b-4d23-8634-a71a99f27fc8_0221o120009kl82y2482D_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (7,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/dfe68cc6-cca4-4ae4-bc0e-44f74d6609f9_0224a120009kk9jnb4C6C_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (8,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e93a1f0d-428c-4613-8c04-883edfa397d7_0225j120009kl826h898C_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (10,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1b8cd8b6-3880-4164-888d-8e0e006f5f9f_0226d120009kk9iidDCDD_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (11,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/97419995-3158-4c96-9baa-53b3665b4f6d_0226q12000aj9k8de1380_R_600_400_R5_D.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (12,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/73345037-223d-4cf6-a612-e07c93b370c6_02245120009kl82rcA53F_R_600_400_R5_D.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (13,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/aa20e9b5-54a0-43b2-84e9-b2fdb75303b8_200213000000v467eBDB3_R_600_400_R5_D.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (14,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/70ce864d-e738-4882-960b-2d9113b508a8_200913000000uzhto5F65_R_600_400_R5_D.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (15,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c94f4bf5-1792-471b-a340-756ea09deffc_220713000000v0aw75AD8_R_600_400_R5_D.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (16,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/4d4a5e7c-9b57-4bfe-aeb9-b38520c9a7cf_0200t120008dpltjhE497_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (17,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b2629dce-bb84-4d33-8f31-7469a39104fc_0202l120008dplol597E5_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (18,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7f3a1252-15a3-4399-9795-fcbc157f0026_0203b120008ru8wjmE8A4_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (19,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/bef7f144-bafe-418f-afeb-3a16185ecd72_0205w120008ru8tc55B16_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (20,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/4e191570-8a50-4130-8d43-d2a333c6277e_0205y120008ru8wjhFB2E_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (21,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/16e8d635-d5b5-4e14-a558-6316b67677bb_0220l120009kk9gkz1818_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (22,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/99b025ec-7f35-4454-8c60-52ddce8821f4_0225u120009kmd2d839D8_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (23,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/dfaa6fd4-947a-443e-a678-6dcfecec6d52_02008120008ru8u9o64FA_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (24,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/445c19d7-8781-413c-83ec-2fab31665c8c_02053120008ru8tuxC131_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (26,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0ccf1afa-1897-447e-87a7-fdc9b7ba2bd2_02240120009kmd488192E_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (27,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/796fcf02-733b-4ef2-bff2-649d95e2eb56_200j13000000uwludCC47_R_600_400_R5_D.jpg',
        false, 3);
INSERT INTO public.property_image
VALUES (28,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/dfaa3052-0e0c-45ae-b205-75d81e0d5742_0205e120009atkpb324FF_R_600_400_R5_D.jpg',
        false, 3);
INSERT INTO public.property_image
VALUES (29,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/97f55cc7-f763-4ebc-9257-e509016e0557_220a12000000rptueE423_R_600_400_R5_D.jpg',
        false, 3);
INSERT INTO public.property_image
VALUES (30,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/be64e2ae-c807-401c-abd3-92b96d8e42f4_220d13000000ui4zs052B_R_600_400_R5_D.jpg',
        false, 3);
INSERT INTO public.property_image
VALUES (31,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/bb391c22-b8bf-45fb-b75d-ac0b44b9ad3e_0221b120009kl7zh7D3EE_R_600_400_R5_D.jpg',
        false, 3);
INSERT INTO public.property_image
VALUES (32,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/55ca5b6b-b418-4011-8dac-983a6e750126_0221o120009kl82y2482D_R_600_400_R5_D.jpg',
        false, 3);
INSERT INTO public.property_image
VALUES (33,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8a3fea3f-023f-447b-bf62-0d8fea4dc496_0224a120009kk9jnb4C6C_R_600_400_R5_D.jpg',
        false, 4);
INSERT INTO public.property_image
VALUES (34,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/78ae300a-d8f5-42dc-8d12-f452dd4f0743_0225j120009kl826h898C_R_600_400_R5_D.jpg',
        false, 4);
INSERT INTO public.property_image
VALUES (35,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7fb16b14-c48b-4d5b-851e-58bda2664278_0226b120009kl7w6mC62A_R_600_400_R5_D.jpg',
        false, 4);
INSERT INTO public.property_image
VALUES (36,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/df7765da-1c1d-4251-920e-3b6cc0b5ccb6_0226d120009kk9iidDCDD_R_600_400_R5_D.jpg',
        false, 4);
INSERT INTO public.property_image
VALUES (37,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7b9a1bf1-a01a-43c2-b2c8-2ee0b8e8838c_0226q12000aj9k8de1380_R_600_400_R5_D.jpg',
        false, 4);
INSERT INTO public.property_image
VALUES (38,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7c091784-940b-4d2b-9bc1-41d5a9b38c01_02245120009kl82rcA53F_R_600_400_R5_D.jpg',
        false, 4);
INSERT INTO public.property_image
VALUES (39,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/a6cfed12-5f76-41c3-b25c-a1a2a30d6b62_200213000000v467eBDB3_R_600_400_R5_D.jpg',
        false, 5);
INSERT INTO public.property_image
VALUES (40,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/cce33077-e7e5-435d-bcda-3d7e1d611c82_200913000000uzhto5F65_R_600_400_R5_D.jpg',
        false, 6);
INSERT INTO public.property_image
VALUES (42,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0091d586-38e6-455f-8c29-feeb05c49603_200e13000000uzutlEC16_R_200_133_R5_D.jpg',
        false, 6);
INSERT INTO public.property_image
VALUES (43,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/712fb46f-5bf9-4ea3-a03a-fd5c72b30a78_200g13000000uxspu5F81_W_1280_853_R5_Q70.jpg',
        false, 6);
INSERT INTO public.property_image
VALUES (45,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/5547e7e5-3aea-435f-8136-f2690f5f398f_200n13000000uw5zv01FD_W_1280_853_R5_Q70.jpg',
        false, 6);
INSERT INTO public.property_image
VALUES (46,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/25f7e0c2-9f1f-49e8-9f31-109bbe90657e_0204c120008ru72ni241A_W_1280_853_R5_Q70.jpg',
        false, 5);
INSERT INTO public.property_image
VALUES (47,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b90ebdc3-6a5e-4a0b-8269-4d7055db7090_02042120009atkjek3DB5_W_1280_853_R5_Q70.jpg',
        false, 5);
INSERT INTO public.property_image
VALUES (48,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f78da7ab-1558-4909-b7d9-7593635f2456_200113000000va14hEF2B_W_1280_853_R5_Q70.jpg',
        false, 5);
INSERT INTO public.property_image
VALUES (49,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0ec700a9-1cfc-4387-8d27-b397ded57bb3_200313000000uyu1v12A5_W_1280_853_R5_Q70.jpg',
        false, 5);
INSERT INTO public.property_image
VALUES (50,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/cfd397ce-c7dc-4773-b0e7-ef6b98420bb9_200e13000000uzutlEC16_R_200_133_R5_D.jpg',
        false, 5);
INSERT INTO public.property_image
VALUES (51,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e3033e73-6b65-4e20-b6ca-659e140e3984_200g13000000uxspu5F81_W_1280_853_R5_Q70.jpg',
        false, 7);
INSERT INTO public.property_image
VALUES (53,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0e60aff0-dbef-482d-aaff-2b4b0ab506fb_200n13000000uw5zv01FD_W_1280_853_R5_Q70.jpg',
        false, 7);
INSERT INTO public.property_image
VALUES (54,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/382782a8-b724-45d8-a542-45357c2c47fd_0204c120008ru72ni241A_W_1280_853_R5_Q70.jpg',
        false, 7);
INSERT INTO public.property_image
VALUES (55,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0108ecbc-866f-44b9-a40c-d2679dbe2cae_02042120009atkjek3DB5_W_1280_853_R5_Q70.jpg',
        false, 7);
INSERT INTO public.property_image
VALUES (56,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e3ed5dc1-4d33-4f61-aaae-45046c01e3ba_200113000000va14hEF2B_W_1280_853_R5_Q70.jpg',
        false, 7);
INSERT INTO public.property_image
VALUES (57,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/25a4cb46-642c-478f-b0ea-70844069e9ae_200313000000uyu1v12A5_W_1280_853_R5_Q70.jpg',
        false, 8);
INSERT INTO public.property_image
VALUES (58,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/de48f71d-5846-4034-8e30-13de21db1c80_1mc2g12000c9tka5s9802_W_1280_853_R5_Q70.jpg',
        false, 8);
INSERT INTO public.property_image
VALUES (59,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b528dd69-7eb3-460a-9ec3-311c6896fe9c_1mc2x12000c9tljhw993E_W_1280_853_R5_Q70.jpg',
        false, 8);
INSERT INTO public.property_image
VALUES (60,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/3a5fa1a0-88f4-4f05-956d-bbf7b4131e93_1mc4l12000c9tljxf4B69_W_1280_853_R5_Q70.jpg',
        false, 8);
INSERT INTO public.property_image
VALUES (61,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/3bebafa7-ce9b-4a12-93a5-a5d3fc0ee14e_1mc5r12000c9tkxut75BC_W_1280_853_R5_Q70.jpg',
        false, 8);
INSERT INTO public.property_image
VALUES (62,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9a88e6b8-1b70-41ca-bbb8-b61df73ad10c_1mc6f12000c9tlmbe0F82_W_1280_853_R5_Q70.jpg',
        false, 8);
INSERT INTO public.property_image
VALUES (63,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9b69c231-2e6a-4129-9981-56182f56e136_1mc5512000c9tll4r8D35_W_1280_853_R5_Q70.jpg',
        false, 8);
INSERT INTO public.property_image
VALUES (64,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/5430f107-56e6-4b11-a123-067e95e662fc_1mc6812000c9tkz3dBBFE_W_1280_853_R5_Q70.jpg',
        false, 8);
INSERT INTO public.property_image
VALUES (65,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7d570d8f-c9b1-49ba-84a2-e685cde054c7_0201y1200085gkisiFF7B_W_1280_853_R5_Q70.jpg',
        false, 8);
INSERT INTO public.property_image
VALUES (66,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8ea9c59b-126f-47a4-a097-e8a381cbf821_0202p120008s5qxgy1581_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (67,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/727ffe0f-b7f0-4226-ae5d-5d34b372742c_0203v1200085m5jyw3B38_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (69,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f24e62bf-e7f0-4ced-a0f6-c363aadf847f_22060g0000007xpkk714D_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (70,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/4ea64495-7635-44ee-bcfc-5d012c88a9ba_1mc2g12000c9tka5s9802_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (71,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c4806c11-e47c-4643-9ffd-7241fd96f1ed_1mc2x12000c9tljhw993E_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (72,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f2211e9e-1aa0-4197-a786-b3f7b077c3f1_1mc4l12000c9tljxf4B69_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (73,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c27a6b13-3a17-4083-911e-ebb2bcd07ec1_1mc5r12000c9tkxut75BC_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (74,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/5a37ba2f-3d21-4b39-9818-f8c049acfa03_1mc6f12000c9tlmbe0F82_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (75,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/fb990e28-3d22-4a6e-8ba1-229c8d9afe89_1mc5512000c9tll4r8D35_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (76,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/03ebde1d-a258-40d6-aa21-422c6d86ce44_1mc6812000c9tkz3dBBFE_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (77,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/30718143-a744-4eda-892c-bbf12c576308_0201y1200085gkisiFF7B_W_1280_853_R5_Q70.jpg',
        false, 10);
INSERT INTO public.property_image
VALUES (78,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/4be6c76c-6f98-4ee6-81a2-015f741166e3_0202p120008s5qxgy1581_W_1280_853_R5_Q70.jpg',
        false, 10);
INSERT INTO public.property_image
VALUES (79,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/6406ffbd-8b62-478e-aa94-21dc8d8ff772_0203v1200085m5jyw3B38_W_1280_853_R5_Q70.jpg',
        false, 10);
INSERT INTO public.property_image
VALUES (80,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/08e447df-c2a8-4b33-a2d4-ed8cfa44f14a_220b0g0000007xrnsFBB7_W_1280_853_R5_Q70.jpg',
        false, 10);
INSERT INTO public.property_image
VALUES (81,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1d1d882b-6e7b-4873-b5c8-ba6c9c86e9b4_22060g0000007xpkk714D_W_1280_853_R5_Q70.jpg',
        false, 10);
INSERT INTO public.property_image
VALUES (82,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8f2324d3-50b6-4018-9e49-3edd28b85956_1mc6m12000caehw9u2F74_W_1280_853_R5_Q70.jpg',
        false, 10);
INSERT INTO public.property_image
VALUES (83,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/237b6da2-522f-4e1d-ba55-8dfaef73be06_0201i120009t6yjzaE974_W_1280_853_R5_Q70.jpg',
        false, 10);
INSERT INTO public.property_image
VALUES (85,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c768e8ac-2c2f-4670-b918-d17441071386_0202w120009t6yng1E1EC_W_1280_853_R5_Q70.jpg',
        false, 11);
INSERT INTO public.property_image
VALUES (86,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/a22da4f9-ca64-4e13-8a36-ab3f8ab0460b_0204a120009t6yl369CA9_W_1280_853_R5_Q70.jpg',
        false, 11);
INSERT INTO public.property_image
VALUES (1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8fc1eecf-9862-4d47-ba21-6e9755582206_200j13000000uwludCC47_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1d944ebc-fe21-41f2-9f68-dbdfb0fc1a39_0226b120009kl7w6mC62A_R_600_400_R5_D.jpg',
        false, 1);
INSERT INTO public.property_image
VALUES (25,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e0ebb67d-8557-4315-b2d3-fcad5c71b410_02058120008ru8jc0BA47_W_1280_853_R5_Q70.jpg',
        false, 2);
INSERT INTO public.property_image
VALUES (41,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/096b3abe-3e3d-4baf-ab0d-ebe432d0ea72_220713000000v0aw75AD8_R_600_400_R5_D.jpg',
        false, 6);
INSERT INTO public.property_image
VALUES (44,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/a5c3bad6-fc44-4136-aecb-2b248616049c_200n13000000uw5zpE1BB_W_1280_853_R5_Q70.jpg',
        false, 6);
INSERT INTO public.property_image
VALUES (52,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/d291e74b-0a09-4f16-824f-18f7fa19cf54_200n13000000uw5zpE1BB_W_1280_853_R5_Q70.jpg',
        false, 7);
INSERT INTO public.property_image
VALUES (68,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/bf0b54b4-1925-4e21-9f59-e4f902930d5d_220b0g0000007xrnsFBB7_W_1280_853_R5_Q70.jpg',
        false, 9);
INSERT INTO public.property_image
VALUES (84,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/89cd0599-d77b-4b54-8735-e2ae21bf8bde_0201t120009t6ynnb0671_W_1280_853_R5_Q70.jpg',
        false, 10);
INSERT INTO public.property_image
VALUES (87,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1e29c9dc-f642-448d-96c6-52685eeb3ead_0206g120009t6yng545A3_W_1280_853_R5_Q70.jpg',
        false, 11);
INSERT INTO public.property_image
VALUES (88,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/ff212606-ea48-4670-9c1b-f1c82087cda6_02010120009t6yoyz56FC_W_1280_853_R5_Q70.jpg',
        false, 11);
INSERT INTO public.property_image
VALUES (89,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/939e6ec3-c383-4365-bdaa-132a9de422dc_02021120009t6yq0804FB_W_1280_853_R5_Q70.jpg',
        false, 11);
INSERT INTO public.property_image
VALUES (90,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0ea9da48-4e61-4856-b1a5-e82f38cf4dc0_02041120009t6ymwc6657_W_1280_853_R5_Q70.jpg',
        false, 11);
INSERT INTO public.property_image
VALUES (91,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0fd8bf16-b834-424e-be64-ddb449c3f408_02273120009zt03as5A51_W_1280_853_R5_Q70.jpg',
        false, 12);
INSERT INTO public.property_image
VALUES (92,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/31a8744c-e6c2-4289-9d57-64c1e2aff7ea_1mc0w12000cae65f5A5C3_W_1280_853_R5_Q70.jpg',
        false, 12);
INSERT INTO public.property_image
VALUES (93,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/82976146-b340-45bb-8168-243639a52d8a_1mc1e12000cae645k738F_W_1280_853_R5_Q70.jpg',
        false, 12);
INSERT INTO public.property_image
VALUES (94,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/df9755aa-2a29-4910-a9de-13a3e4ef7f2e_1mc1p12000cae67nsE0FC_W_1280_853_R5_Q70.jpg',
        false, 12);
INSERT INTO public.property_image
VALUES (95,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9aa32604-da82-462f-9b5c-dfbf2b956a18_1mc4n12000bzdwqho8703_W_1280_853_R5_Q70.jpg',
        false, 13);
INSERT INTO public.property_image
VALUES (96,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/3473cdb8-1ffe-486c-ade5-e5059bdc7aee_1mc6312000cae61poF766_W_1280_853_R5_Q70.jpg',
        false, 13);


--
-- TOC entry 4793 (class 0 OID 18549)
-- Dependencies: 272
-- Data for Name: time_frame; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.time_frame
VALUES (1, '2023-10-01 07:00:00', '2023-10-07 07:00:00', false, 'PENDING', 1, 5, '001');
INSERT INTO public.time_frame
VALUES (2, '2023-10-08 07:00:00', '2023-10-14 07:00:00', false, 'PENDING', 4, 5, '002');
INSERT INTO public.time_frame
VALUES (3, '2023-10-15 07:00:00', '2023-10-21 07:00:00', false, 'PENDING', 5, 5, '002');
INSERT INTO public.time_frame
VALUES (4, '2023-10-22 07:00:00', '2023-10-28 07:00:00', false, 'PENDING', 7, 5, '002');
INSERT INTO public.time_frame
VALUES (5, '2023-10-29 07:00:00', '2023-11-04 07:00:00', false, 'PENDING', 8, 5, '002');
INSERT INTO public.time_frame
VALUES (6, '2023-11-05 07:00:00', '2023-11-11 07:00:00', false, 'PENDING', 9, 5, '002');
INSERT INTO public.time_frame
VALUES (7, '2023-11-12 07:00:00', '2023-11-18 07:00:00', false, 'PENDING', 10, 5, '002');
INSERT INTO public.time_frame
VALUES (8, '2023-11-22 07:00:00', '2023-11-28 07:00:00', false, 'PENDING', 12, 5, '002');
INSERT INTO public.time_frame
VALUES (9, '2023-11-29 07:00:00', '2023-12-04 07:00:00', false, 'PENDING', 13, 5, '001');


--
-- TOC entry 4782 (class 0 OID 18511)
-- Dependencies: 261
-- Data for Name: available_time; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--


--
-- TOC entry 4766 (class 0 OID 18456)
-- Dependencies: 245
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--


--
-- TOC entry 4812 (class 0 OID 18629)
-- Dependencies: 291
-- Data for Name: resort_amenity_type; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.resort_amenity_type
VALUES (1, 'Most Popular Amenities', 'Most Popular Amenities', false);
INSERT INTO public.resort_amenity_type
VALUES (2, 'Shipping services', 'Shipping services', false);
INSERT INTO public.resort_amenity_type
VALUES (3, 'Activate', 'Activate', false);
INSERT INTO public.resort_amenity_type
VALUES (4, 'Medical-Health', 'Medical-Health', false);
INSERT INTO public.resort_amenity_type
VALUES (5, 'Reception service', 'Reception service', false);
INSERT INTO public.resort_amenity_type
VALUES (6, 'Transportation Services', 'Transportation Services', false);
INSERT INTO public.resort_amenity_type
VALUES (7, 'Recreational Facilities', 'Recreational Facilities', false);
INSERT INTO public.resort_amenity_type
VALUES (8, 'Health & Wellness', 'Health & Wellness', false);
INSERT INTO public.resort_amenity_type
VALUES (9, 'Front Desk Services', 'Front Desk Services', false);
INSERT INTO public.resort_amenity_type
VALUES (10, 'Cleaning Services', 'Cleaning Services', false);
INSERT INTO public.resort_amenity_type
VALUES (11, 'Dining Services', 'Dining Services', false);
INSERT INTO public.resort_amenity_type
VALUES (12, 'Public Areas', 'Public Areas', false);
INSERT INTO public.resort_amenity_type
VALUES (13, 'Business Services', 'Business Services', false);
INSERT INTO public.resort_amenity_type
VALUES (14, 'Safety & Security', 'Safety & Security', false);
INSERT INTO public.resort_amenity_type
VALUES (15, 'Sports', 'Sports', false);
INSERT INTO public.resort_amenity_type
VALUES (16, 'Kid''s Facilities', 'Kid''s Facilities', false);


--
-- TOC entry 4810 (class 0 OID 18620)
-- Dependencies: 289
-- Data for Name: resort_amenity; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.resort_amenity
VALUES (1, 'Outdoor Swimming Pool', 'Outdoor Swimming Pool', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e20310d5-e022-4b0a-b8c7-fae6525fc42d_2784593.png');
INSERT INTO public.resort_amenity
VALUES (2, 'Wi-Fi in Public Areas', 'Wi-Fi in Public Areas', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b84635af-96a6-435c-b7d2-0d4d3f7d43d7_11530392.png');
INSERT INTO public.resort_amenity
VALUES (3, 'Airport Shuttle Service', 'Airport Shuttle Service', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8f3e0474-fc3a-47ef-a93b-1138c72354be_Airport%20Shuttle%20Service.png');
INSERT INTO public.resort_amenity
VALUES (4, 'Airport Pick-Up Service', 'Airport Pick-Up Service', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/50660c3f-4573-447a-b6bb-39aa9d019e77_Airport%20Pick-Up%20Service.png');
INSERT INTO public.resort_amenity
VALUES (5, 'Restaurant', 'Restaurant', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b4c29af0-9f0d-4eb8-b07e-a66176ac75e3_Restaurant.png');
INSERT INTO public.resort_amenity
VALUES (6, 'Bar', 'Bar', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8b969d73-7fb6-49a4-9f46-ea06536a1391_Bar.png');
INSERT INTO public.resort_amenity
VALUES (7, 'Non-Smoking Floor', 'Non-Smoking Floor', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e81f1208-f491-4637-a3f6-bedb9c52032b_Non-Smoking%20Floor.png');
INSERT INTO public.resort_amenity
VALUES (8, 'Currency Exchange', 'Currency Exchange', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9e4aa4c2-5633-425f-9426-76255724d439_Currency%20Exchange.png');
INSERT INTO public.resort_amenity
VALUES (9, 'Fitness Room', 'Fitness Room', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/363e9136-af79-419b-baf8-1a17f3b1a901_Fitness%20Room.png');
INSERT INTO public.resort_amenity
VALUES (10, 'Sauna', 'Sauna', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f93dc181-1117-4326-be10-4f206e535cab_Sauna.png');
INSERT INTO public.resort_amenity
VALUES (11, 'Massage Room', 'Massage Room', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/4a275320-8015-4a47-8916-71b9d9c4c35a_Massage%20Room.png');
INSERT INTO public.resort_amenity
VALUES (12, 'Luggage Storage', 'Luggage Storage', false, 1, 'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/cb4fb8d1-0c00-4c7c-98c2-e182d0a4d95d_Luggage%20Storage.png
Response headers');
INSERT INTO public.resort_amenity
VALUES (13, 'Spa', 'Spa', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/855634ab-3b74-4846-a60a-20470231ef6f_Spa.png');
INSERT INTO public.resort_amenity
VALUES (14, 'Sunbathing Area', 'Sunbathing Area', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/5a2fce36-fbce-4c00-b571-dabf8e230936_Sunbathing%20Area.png');
INSERT INTO public.resort_amenity
VALUES (15, 'Meeting Room', 'Meeting Room', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9dda445e-6be8-43d7-9493-3959f3b77b52_Meeting%20Room.png');
INSERT INTO public.resort_amenity
VALUES (16, 'Languages Spoken at Front Desk', 'Languages Spoken at Front Desk', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c0c42504-9218-4a13-86e0-cf5d36937b28_Languages%20Spoken.png');
INSERT INTO public.resort_amenity
VALUES (17, 'Kids'' Club', 'Kids'' Club', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/386ffd84-dd4e-43b6-9df4-ab57730b4d3e_Kids%27%20Club.png');
INSERT INTO public.resort_amenity
VALUES (18, 'Children''s Playground', 'Children''s Playground', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/783d8b95-208e-4095-aa57-28b7fc7e9af8_Children%27s%20Playground.png');
INSERT INTO public.resort_amenity
VALUES (19, 'Business Center', 'Business Center', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/18a6e39c-d7f6-4efa-ae53-e63c5adbe48f_Business%20Center.png');
INSERT INTO public.resort_amenity
VALUES (20, 'Executive Floor', 'Executive Floor', false, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e24d9f19-b9c1-451b-a907-dd2cdf865bab_Executive%20Floor.jpg');
INSERT INTO public.resort_amenity
VALUES (21, 'Airport Drop-Off Service', 'Airport Drop-Off Service', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/60e2ae97-ab1e-4a10-bbbc-511dab27c46e_Airport%20Drop-Off%20Service.png');
INSERT INTO public.resort_amenity
VALUES (22, 'Concierge Car Service', 'Concierge Car Service', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/30400b41-db51-4978-a47d-364b8c88dc44_Concierge%20Car%20Service.png');
INSERT INTO public.resort_amenity
VALUES (23, 'Bicycle Rentals', 'Bicycle Rentals', false, 6,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/080162f0-baa1-40a2-827f-153f08b25bc2_Bicycle%20Rentals.png');
INSERT INTO public.resort_amenity
VALUES (24, 'Beach Bar', 'Beach Bar', false, 7,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9ef4bbc9-3ffe-4a16-bc20-f78c1a24806a_Beach%20Bar.png');
INSERT INTO public.resort_amenity
VALUES (25, 'Beach Towel', 'Beach Towel', false, 7,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8cc25e54-bfff-41a2-8eae-eaefbbe218c5_Beach%20Towel.png');
INSERT INTO public.resort_amenity
VALUES (26, 'Public Beach', 'Public Beach', false, 7,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/a7edf159-5e2a-48aa-a9ef-fd645a3ed633_Public%20Beach.png');
INSERT INTO public.resort_amenity
VALUES (27, 'Full-Service Spa', 'Full-Service Spa', false, 8,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/af7942a5-8106-493d-ba3a-514906df5309_Full-Service%20Spa.png');
INSERT INTO public.resort_amenity
VALUES (28, 'Porter/Bellhop', 'Porter/Bellhop', false, 9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/ce23cada-f4c9-4011-b2d6-1e15626ba2c2_Porter.png');
INSERT INTO public.resort_amenity
VALUES (29, '24-Hour Front Desk', '24-Hour Front Desk', false, 9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c8475b55-dfce-4a32-98d3-b04dcec4052f_24-Hour%20Front%20Desk.png');
INSERT INTO public.resort_amenity
VALUES (30, 'Concierge Service', 'Concierge Service', false, 9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/2aaba782-e1a9-4533-bd0d-5de4192af6bf_Concierge%20Service.png');
INSERT INTO public.resort_amenity
VALUES (31, 'Front Desk Safe', 'Front Desk Safe', false, 9,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b202a01b-7f6e-4e28-9c9c-855e42dbce86_Front%20Desk%20Safe.png');
INSERT INTO public.resort_amenity
VALUES (34, 'Laundry Service', 'Laundry Service', false, 10,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/41c5fa6b-96c4-44bc-b7e1-97201df21461_Laundry%20Service.png');
INSERT INTO public.resort_amenity
VALUES (37, 'Elevator', 'Elevator', false, 12,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7e39f34f-f0a1-4d4c-a9b3-18c7c945f690_Elevator.png');
INSERT INTO public.resort_amenity
VALUES (39, 'No Smoking in Public Areas', 'No Smoking in Public Areas', false, 12,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f2b7796a-1df0-495b-b1d3-2165d7f65626_Non-Smoking%20Floor.png');
INSERT INTO public.resort_amenity
VALUES (42, 'Security Alarm', 'Security Alarm', false, 14,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/a0ffff33-e4c6-4293-937b-c1eff78b5629_Security%20Alarm.png');
INSERT INTO public.resort_amenity
VALUES (45, 'Fire Extinguisher', 'Fire Extinguisher', false, 14,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/4ef2b69f-e0f3-4677-93ff-b41c651912da_Fire%20Extinguisher.png');
INSERT INTO public.resort_amenity
VALUES (48, 'Security Personnel', 'Security Personnel', false, 14,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7ed72db5-63a3-4bbf-9f55-5a7f8697243e_Security%20Personnel.png');
INSERT INTO public.resort_amenity
VALUES (32, 'Guest Laundry', 'Guest Laundry', false, 10,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/41b80c72-0f51-4589-b30f-d544f9c0d76b_Guest%20Laundry.png');
INSERT INTO public.resort_amenity
VALUES (33, 'Ironing Service', 'Ironing Service', false, 10,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f0110bfa-07a7-4ed2-bb01-cbb27f7e09fa_Ironing%20Service.png');
INSERT INTO public.resort_amenity
VALUES (35, 'Dry Cleaning', 'Dry Cleaning', false, 10,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/ab8a632e-3169-4313-be9f-9bd216d8e27b_Dry%20Cleaning.png');
INSERT INTO public.resort_amenity
VALUES (36, 'Room Service', 'Room Service', false, 11,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9ede7d54-71e8-45a4-9aad-1b76d0393f5e_Room%20Service.png');
INSERT INTO public.resort_amenity
VALUES (38, 'Non-Smoking in All Rooms', 'Non-Smoking in All Rooms', false, 12,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f2b7796a-1df0-495b-b1d3-2165d7f65626_Non-Smoking%20Floor.png');
INSERT INTO public.resort_amenity
VALUES (40, 'Garden', 'Garden', false, 12,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/d9e34465-9f00-4ba8-ac3d-398beb84d03e_Garden.png');
INSERT INTO public.resort_amenity
VALUES (41, 'CCTV In Public Areas', 'CCTV In Public Areas', false, 13,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/aebf69c7-5cf4-415e-8493-5069ae13a059_CCTV%20In%20Public%20Areas.png');
INSERT INTO public.resort_amenity
VALUES (43, 'Fire Alarm', 'Fire Alarm', false, 14,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f0993358-d2c5-406c-bc33-f41d37c3ed74_Fire%20Alarm.png');
INSERT INTO public.resort_amenity
VALUES (44, 'Smoke Detector', 'Smoke Detector', false, 14,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/5823fe6a-b0c2-4592-9baf-fbb40e3780fd_Smoke%20Detector.png');
INSERT INTO public.resort_amenity
VALUES (47, 'First-Aid Kit', 'First-Aid Kit', false, 14,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/64b2cb9a-de14-4ace-ab4f-9334bbf4fb0a_First-Aid%20Kit.png');
INSERT INTO public.resort_amenity
VALUES (46, 'First Aid Services', 'First Aid Services', false, 14,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0d16cf86-93c4-4be1-b7a0-a394359bbe02_First%20Aid%20Services.png');


--
-- TOC entry 4770 (class 0 OID 18472)
-- Dependencies: 249
-- Data for Name: resort_image; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.resort_image
VALUES (1, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8a0f7989-ef70-4826-9ca9-70473e5f60e0_1mc3o12000ap9msmw1F6F_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (2, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1cd86edd-d687-4018-a607-64b12579a396_1mc4712000ap9mcan6CAB_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (3, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/cee4be25-ef6f-463a-9d01-43bb9b74266a_200h13000000v906y9870_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (4, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/07597561-b655-4cce-a5cf-ade169bb6083_0202k120008ru71i066A0_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (5, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/52acd456-6ea8-4af5-97a2-71a1ffbb6320_0204m1200091xvzy831D8_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (6, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/01543140-77fe-41de-945f-32a06ed3d23b_0220m1200093svhl0638C_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (7, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e68c7579-f267-4cf9-9fde-fecca9a97dc2_0225e1200093svjdqCC75_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (8, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9686f9c3-6c08-4ace-be01-6afa9316d9ad_0226b12000ar8c2ak9FC9_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (9, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/35f35c2a-2dfa-4714-9d3b-30fc4c43d724_0226912000ar8bwnqD703_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (10, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/aff21e54-2f63-477d-9e9f-f0d59ee2e57e_020211200091xw09c1A3E_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (11, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/dbe6b09c-cfef-4ce7-b7de-a786b18d2597_020691200091xw20jD45A_R_600_400_R5_D.jpa.jpg',
        false);
INSERT INTO public.resort_image
VALUES (12, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/10148f4c-b7a8-44c5-8335-114835e3d29c_1mc4k12000adtoup0C094_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (13, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/fd6bedf3-4ca4-4a77-97bc-c9d48e806915_1mc4212000ae01817ADD8_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (14, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/74d676a2-9f1b-4476-82d8-f78f5afd9677_220a0r000000gw5rv99E2_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (15, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/49417e7b-bddd-4880-bb8e-90b8f98b9b50_220b0g0000007xrnuDB7D_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (16, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/8463964e-0242-4c3f-866c-8a70e3218299_220e0r000000gxl2r9F31_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (17, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7aa56752-c401-40ac-9d73-1255aaa37e8c_220f0g0000007xpa56E13_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (18, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b4f38168-2dd4-4005-88bc-d2bf17c7389d_220g0g0000007xp72C9CC_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (19, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/ea0df12b-fc84-4f5d-ad6b-48d7acbc6b8d_220g0r000000gvj3sADAC_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (20, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/713bfdd1-39c0-4eaf-898b-9282641d6831_220j0r000000gucna50F1_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (21, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/2d0744a1-3aa3-4491-a88a-c471130eb565_220o0g0000007xqdt5BDF_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (22, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/a56a915f-6c9d-4ae1-a92f-16b75159f183_220o0r000000gtojd9C1B_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (23, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/49700de3-7b96-42e5-8488-4f7353675660_220o14000000vzfac9BC1_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (24, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/d8a51559-5dd0-4b12-a57c-4ba70d09edb9_220p0r000000gtcpuD332_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (25, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/98052b95-b929-4aba-85b5-00ccbfcb675d_220p12000000rixbz62B7_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (26, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/57c0231b-93a4-467e-a26d-193f19abe2d6_0226x12000a8jwm6bAD29_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (27, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/ddef4d08-bee6-4a00-9b42-6270db5ceb5f_22010g0000007xre490F0_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (28, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b342d573-286c-44c1-9d76-0b0935b9424d_22030g0000007xqnkB7A5_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (29, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/fba5b896-e2e6-40e8-aa69-685f1c9f54fb_22070g0000007xrjq8086_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (30, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/ead3df1a-1cd0-4485-b7d7-5a27f10d082d_22080r000000gv11w1F61_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (31, 2,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c4f98dc2-ae38-4a10-939e-0aaf30fcdec5_22090r000000gx8qb8973_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (32, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/3c2c5dec-2805-4a74-8f32-f87e8455e867_1mc3y12000caecxf588BE_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (33, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/3dcf784d-acd3-421b-864b-b902cf2e1e06_0200l120009t6v4vvDFF4_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (34, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/7b2a0792-5a46-472f-ae7b-c8ea35f0a1b6_0201j120009t6uk3r4713_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (35, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c7cc48b9-3b3a-411e-92fc-11740a7ac99d_0202k120009t6vcf90AC2_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (36, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/30984ad6-257d-4379-97bd-43784f8300d9_0203l120009t6uv8bC99C_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (37, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e44d6534-2739-4292-99a3-d70ec941fc12_0221k12000akya2yiDF31_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (38, 3,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/10ed8fa6-67f9-4dd9-92e6-8ce364afd633_0222012000a2f7t6d5020_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (39, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9b73491d-1925-4a44-938d-85fced428f73_1mc2x12000c18gzm40D7A_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (40, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c2236e23-072c-435e-8098-c2f9544fbad5_1mc0112000c041axi42E2_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (41, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/a6ff5656-b307-47a9-87e7-2d370a9b8276_200m0v000000jzclaF291_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (42, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/50523b09-b1f7-4102-bf97-15cb787d7ca5_0205v12000a6mbrxz14B4_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (43, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1950291f-5529-4852-83d3-23c0d4886a96_0220f120009hmqkliAC82_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (44, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/c0af2c8b-c77a-4a75-b4b4-50f1aae3cfb7_0201512000a6mo86s077F_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (45, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/2fd97ea7-edba-4308-9e4c-e55573fbe7e9_0203112000a6d7dfiF944_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (46, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/562a202b-3f20-44b2-be26-21775526a5f9_0204012000a6mnhfbC136_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (47, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0ecbe35e-3c8d-43fd-85c6-6f5572c53472_02206120009h0f4t2C5A3_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (48, 4,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/80f47894-a7c1-4ad0-95a9-0e481a2555ba_02228120008ktzra6A917_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (49, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/77426f34-b3f8-4c1f-aff5-686684756251_1mc1v12000bbchq3g37A3_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (50, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1ae4047e-91be-47c9-875f-4c79b32bcff3_1mc3i12000avlexrhB07D_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (51, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/81c8e3ad-9b0b-4118-bebf-f3c8ca5cb03b_1mc4f12000avlev9lB99C_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (52, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/5cc35956-1b98-4eca-95e3-074cd26decfe_1mc1712000avlep17C589_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (53, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/79e9bcde-f833-400d-9323-db33f2012a28_1mc3412000avlep8t244B_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (54, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/2890e6f7-1fbd-467c-8c04-80ba04e60d78_0220v12000b4fzdeg8EF9_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (55, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/824a805a-d84f-407f-b45c-80527ece2346_0221e12000bozg2nv60B0_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (56, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9a3e1e64-16b4-4854-b783-8f08435e6fd8_0222x12000bozfp4aE1EF_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (57, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/1977bca1-1d3e-422b-8a30-b7da0e818db2_0223c12000bs1vfej1A6F_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (58, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/12573dff-655d-45b6-a215-87ccbd61fc41_0223m12000b4fzef22A7C_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (59, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/0e4ae00b-ca54-42fb-a9de-e1bf43a3b0ef_0223n12000b4fzefiFB4B_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (60, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9fa3c330-ba7d-4603-bb96-fbc6c4cb3c3f_0224v12000b4fzdk37AC9_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (61, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/125d05af-dd4d-45d6-8332-4922ad4fe95b_0224w12000b4fzdkj93B5_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (62, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/783fbfe7-4bb0-4e29-8def-80a7553dbde4_0225e12000b4fzef6BB45_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (63, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/f24d965c-4068-4c45-890d-ee6a9c1da171_0225o12000b4fzctmF0DA_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (64, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e02d5ce9-2e67-4037-ae93-9c00698131bc_0220812000b4fzc4b7082_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (65, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/9a7016ff-ec4a-47e1-98ab-06089f05ea93_0221112000b4fzc315021_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (66, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/03ecb963-40a3-411a-a2f9-bbb34be96be0_0221912000b4fzdiz0417_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (67, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/39f6bab6-07bb-48e1-9c37-0da764c5f342_0223112000b7ld0ig4346_R_600_400_R5_D.webp',
        false);
INSERT INTO public.resort_image
VALUES (68, 5,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/b85d720d-c842-4be7-af97-7393a38413f7_0225312000b4fzf7z4B1E_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (69, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/13cade31-3176-4dcc-b5a4-bfc60bf0fb3c_1mc4712000ap9mcan6CAB_R_600_400_R5_D.jpg',
        false);
INSERT INTO public.resort_image
VALUES (70, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/ae2c7bb1-1fba-4b98-a156-53130e9b00f8_resort1.jpg',
        false);
INSERT INTO public.resort_image
VALUES (71, 1,
        'https://holiday-swap-file-resouces.s3.ap-southeast-1.amazonaws.com/e876d0b0-f389-4f45-8328-440b894b4ce1_resort2.jpg',
        false);


--
-- TOC entry 4771 (class 0 OID 18480)
-- Dependencies: 250
-- Data for Name: resorts_amenities; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.resorts_amenities
VALUES (1, 1);
INSERT INTO public.resorts_amenities
VALUES (2, 1);
INSERT INTO public.resorts_amenities
VALUES (3, 1);
INSERT INTO public.resorts_amenities
VALUES (4, 1);
INSERT INTO public.resorts_amenities
VALUES (5, 1);
INSERT INTO public.resorts_amenities
VALUES (6, 1);
INSERT INTO public.resorts_amenities
VALUES (7, 1);
INSERT INTO public.resorts_amenities
VALUES (8, 1);
INSERT INTO public.resorts_amenities
VALUES (9, 1);
INSERT INTO public.resorts_amenities
VALUES (10, 1);
INSERT INTO public.resorts_amenities
VALUES (11, 1);
INSERT INTO public.resorts_amenities
VALUES (12, 1);
INSERT INTO public.resorts_amenities
VALUES (13, 1);
INSERT INTO public.resorts_amenities
VALUES (14, 1);
INSERT INTO public.resorts_amenities
VALUES (15, 1);
INSERT INTO public.resorts_amenities
VALUES (16, 1);
INSERT INTO public.resorts_amenities
VALUES (16, 1);
INSERT INTO public.resorts_amenities
VALUES (18, 1);
INSERT INTO public.resorts_amenities
VALUES (19, 1);
INSERT INTO public.resorts_amenities
VALUES (20, 1);
INSERT INTO public.resorts_amenities
VALUES (21, 1);
INSERT INTO public.resorts_amenities
VALUES (22, 1);
INSERT INTO public.resorts_amenities
VALUES (23, 1);
INSERT INTO public.resorts_amenities
VALUES (24, 1);
INSERT INTO public.resorts_amenities
VALUES (25, 1);
INSERT INTO public.resorts_amenities
VALUES (26, 1);
INSERT INTO public.resorts_amenities
VALUES (27, 1);
INSERT INTO public.resorts_amenities
VALUES (28, 1);
INSERT INTO public.resorts_amenities
VALUES (29, 1);
INSERT INTO public.resorts_amenities
VALUES (30, 1);
INSERT INTO public.resorts_amenities
VALUES (31, 1);
INSERT INTO public.resorts_amenities
VALUES (32, 1);
INSERT INTO public.resorts_amenities
VALUES (33, 1);
INSERT INTO public.resorts_amenities
VALUES (34, 1);
INSERT INTO public.resorts_amenities
VALUES (35, 1);
INSERT INTO public.resorts_amenities
VALUES (36, 1);
INSERT INTO public.resorts_amenities
VALUES (37, 1);
INSERT INTO public.resorts_amenities
VALUES (38, 1);
INSERT INTO public.resorts_amenities
VALUES (39, 1);
INSERT INTO public.resorts_amenities
VALUES (40, 1);
INSERT INTO public.resorts_amenities
VALUES (41, 1);
INSERT INTO public.resorts_amenities
VALUES (42, 1);
INSERT INTO public.resorts_amenities
VALUES (43, 1);
INSERT INTO public.resorts_amenities
VALUES (44, 1);
INSERT INTO public.resorts_amenities
VALUES (1, 2);
INSERT INTO public.resorts_amenities
VALUES (2, 2);
INSERT INTO public.resorts_amenities
VALUES (3, 2);
INSERT INTO public.resorts_amenities
VALUES (4, 2);
INSERT INTO public.resorts_amenities
VALUES (11, 2);
INSERT INTO public.resorts_amenities
VALUES (12, 2);
INSERT INTO public.resorts_amenities
VALUES (13, 2);
INSERT INTO public.resorts_amenities
VALUES (14, 2);
INSERT INTO public.resorts_amenities
VALUES (15, 2);
INSERT INTO public.resorts_amenities
VALUES (16, 2);
INSERT INTO public.resorts_amenities
VALUES (16, 2);
INSERT INTO public.resorts_amenities
VALUES (18, 2);
INSERT INTO public.resorts_amenities
VALUES (19, 2);
INSERT INTO public.resorts_amenities
VALUES (20, 2);
INSERT INTO public.resorts_amenities
VALUES (21, 2);
INSERT INTO public.resorts_amenities
VALUES (22, 2);
INSERT INTO public.resorts_amenities
VALUES (23, 2);
INSERT INTO public.resorts_amenities
VALUES (24, 2);
INSERT INTO public.resorts_amenities
VALUES (25, 2);
INSERT INTO public.resorts_amenities
VALUES (26, 2);
INSERT INTO public.resorts_amenities
VALUES (27, 2);
INSERT INTO public.resorts_amenities
VALUES (28, 2);
INSERT INTO public.resorts_amenities
VALUES (29, 2);
INSERT INTO public.resorts_amenities
VALUES (30, 2);
INSERT INTO public.resorts_amenities
VALUES (31, 2);
INSERT INTO public.resorts_amenities
VALUES (39, 2);
INSERT INTO public.resorts_amenities
VALUES (40, 2);
INSERT INTO public.resorts_amenities
VALUES (41, 2);
INSERT INTO public.resorts_amenities
VALUES (42, 2);
INSERT INTO public.resorts_amenities
VALUES (43, 2);
INSERT INTO public.resorts_amenities
VALUES (44, 2);
INSERT INTO public.resorts_amenities
VALUES (1, 3);
INSERT INTO public.resorts_amenities
VALUES (2, 3);
INSERT INTO public.resorts_amenities
VALUES (3, 3);
INSERT INTO public.resorts_amenities
VALUES (4, 3);
INSERT INTO public.resorts_amenities
VALUES (5, 3);
INSERT INTO public.resorts_amenities
VALUES (6, 3);
INSERT INTO public.resorts_amenities
VALUES (7, 3);
INSERT INTO public.resorts_amenities
VALUES (8, 3);
INSERT INTO public.resorts_amenities
VALUES (9, 3);
INSERT INTO public.resorts_amenities
VALUES (10, 3);
INSERT INTO public.resorts_amenities
VALUES (11, 3);
INSERT INTO public.resorts_amenities
VALUES (12, 3);
INSERT INTO public.resorts_amenities
VALUES (13, 3);
INSERT INTO public.resorts_amenities
VALUES (14, 3);
INSERT INTO public.resorts_amenities
VALUES (15, 3);
INSERT INTO public.resorts_amenities
VALUES (16, 3);
INSERT INTO public.resorts_amenities
VALUES (16, 3);
INSERT INTO public.resorts_amenities
VALUES (18, 3);
INSERT INTO public.resorts_amenities
VALUES (19, 3);
INSERT INTO public.resorts_amenities
VALUES (20, 3);
INSERT INTO public.resorts_amenities
VALUES (21, 3);
INSERT INTO public.resorts_amenities
VALUES (22, 3);
INSERT INTO public.resorts_amenities
VALUES (23, 3);
INSERT INTO public.resorts_amenities
VALUES (24, 3);
INSERT INTO public.resorts_amenities
VALUES (25, 3);
INSERT INTO public.resorts_amenities
VALUES (26, 3);
INSERT INTO public.resorts_amenities
VALUES (27, 3);
INSERT INTO public.resorts_amenities
VALUES (28, 3);
INSERT INTO public.resorts_amenities
VALUES (29, 3);
INSERT INTO public.resorts_amenities
VALUES (30, 3);
INSERT INTO public.resorts_amenities
VALUES (31, 3);
INSERT INTO public.resorts_amenities
VALUES (32, 3);
INSERT INTO public.resorts_amenities
VALUES (33, 3);
INSERT INTO public.resorts_amenities
VALUES (34, 3);
INSERT INTO public.resorts_amenities
VALUES (35, 3);
INSERT INTO public.resorts_amenities
VALUES (36, 3);
INSERT INTO public.resorts_amenities
VALUES (37, 3);
INSERT INTO public.resorts_amenities
VALUES (38, 3);
INSERT INTO public.resorts_amenities
VALUES (39, 3);
INSERT INTO public.resorts_amenities
VALUES (40, 3);
INSERT INTO public.resorts_amenities
VALUES (41, 3);
INSERT INTO public.resorts_amenities
VALUES (42, 3);
INSERT INTO public.resorts_amenities
VALUES (43, 3);
INSERT INTO public.resorts_amenities
VALUES (44, 3);
INSERT INTO public.resorts_amenities
VALUES (1, 4);
INSERT INTO public.resorts_amenities
VALUES (2, 4);
INSERT INTO public.resorts_amenities
VALUES (3, 4);
INSERT INTO public.resorts_amenities
VALUES (4, 4);
INSERT INTO public.resorts_amenities
VALUES (5, 4);
INSERT INTO public.resorts_amenities
VALUES (6, 4);
INSERT INTO public.resorts_amenities
VALUES (7, 4);
INSERT INTO public.resorts_amenities
VALUES (8, 4);
INSERT INTO public.resorts_amenities
VALUES (9, 4);
INSERT INTO public.resorts_amenities
VALUES (10, 4);
INSERT INTO public.resorts_amenities
VALUES (11, 4);
INSERT INTO public.resorts_amenities
VALUES (12, 4);
INSERT INTO public.resorts_amenities
VALUES (13, 4);
INSERT INTO public.resorts_amenities
VALUES (14, 4);
INSERT INTO public.resorts_amenities
VALUES (15, 4);
INSERT INTO public.resorts_amenities
VALUES (16, 4);
INSERT INTO public.resorts_amenities
VALUES (16, 4);
INSERT INTO public.resorts_amenities
VALUES (18, 4);
INSERT INTO public.resorts_amenities
VALUES (19, 4);
INSERT INTO public.resorts_amenities
VALUES (20, 4);
INSERT INTO public.resorts_amenities
VALUES (21, 4);
INSERT INTO public.resorts_amenities
VALUES (22, 4);
INSERT INTO public.resorts_amenities
VALUES (23, 4);
INSERT INTO public.resorts_amenities
VALUES (24, 4);
INSERT INTO public.resorts_amenities
VALUES (25, 4);
INSERT INTO public.resorts_amenities
VALUES (26, 4);
INSERT INTO public.resorts_amenities
VALUES (27, 4);
INSERT INTO public.resorts_amenities
VALUES (28, 4);
INSERT INTO public.resorts_amenities
VALUES (29, 4);
INSERT INTO public.resorts_amenities
VALUES (30, 4);
INSERT INTO public.resorts_amenities
VALUES (31, 4);
INSERT INTO public.resorts_amenities
VALUES (32, 4);
INSERT INTO public.resorts_amenities
VALUES (33, 4);
INSERT INTO public.resorts_amenities
VALUES (34, 4);
INSERT INTO public.resorts_amenities
VALUES (35, 4);
INSERT INTO public.resorts_amenities
VALUES (36, 4);
INSERT INTO public.resorts_amenities
VALUES (37, 4);
INSERT INTO public.resorts_amenities
VALUES (38, 4);
INSERT INTO public.resorts_amenities
VALUES (39, 4);
INSERT INTO public.resorts_amenities
VALUES (40, 4);
INSERT INTO public.resorts_amenities
VALUES (41, 4);
INSERT INTO public.resorts_amenities
VALUES (42, 4);
INSERT INTO public.resorts_amenities
VALUES (43, 4);
INSERT INTO public.resorts_amenities
VALUES (44, 4);
INSERT INTO public.resorts_amenities
VALUES (1, 5);
INSERT INTO public.resorts_amenities
VALUES (2, 5);
INSERT INTO public.resorts_amenities
VALUES (3, 5);
INSERT INTO public.resorts_amenities
VALUES (4, 5);
INSERT INTO public.resorts_amenities
VALUES (5, 5);
INSERT INTO public.resorts_amenities
VALUES (6, 5);
INSERT INTO public.resorts_amenities
VALUES (7, 5);
INSERT INTO public.resorts_amenities
VALUES (8, 5);
INSERT INTO public.resorts_amenities
VALUES (9, 5);
INSERT INTO public.resorts_amenities
VALUES (10, 5);
INSERT INTO public.resorts_amenities
VALUES (11, 5);
INSERT INTO public.resorts_amenities
VALUES (12, 5);
INSERT INTO public.resorts_amenities
VALUES (13, 5);
INSERT INTO public.resorts_amenities
VALUES (14, 5);
INSERT INTO public.resorts_amenities
VALUES (15, 5);
INSERT INTO public.resorts_amenities
VALUES (16, 5);
INSERT INTO public.resorts_amenities
VALUES (16, 5);
INSERT INTO public.resorts_amenities
VALUES (18, 5);
INSERT INTO public.resorts_amenities
VALUES (19, 5);
INSERT INTO public.resorts_amenities
VALUES (20, 5);
INSERT INTO public.resorts_amenities
VALUES (21, 5);
INSERT INTO public.resorts_amenities
VALUES (22, 5);
INSERT INTO public.resorts_amenities
VALUES (23, 5);
INSERT INTO public.resorts_amenities
VALUES (24, 5);
INSERT INTO public.resorts_amenities
VALUES (25, 5);
INSERT INTO public.resorts_amenities
VALUES (26, 5);
INSERT INTO public.resorts_amenities
VALUES (27, 5);
INSERT INTO public.resorts_amenities
VALUES (28, 5);
INSERT INTO public.resorts_amenities
VALUES (29, 5);
INSERT INTO public.resorts_amenities
VALUES (30, 5);
INSERT INTO public.resorts_amenities
VALUES (31, 5);
INSERT INTO public.resorts_amenities
VALUES (32, 5);
INSERT INTO public.resorts_amenities
VALUES (33, 5);
INSERT INTO public.resorts_amenities
VALUES (34, 5);
INSERT INTO public.resorts_amenities
VALUES (35, 5);
INSERT INTO public.resorts_amenities
VALUES (36, 5);
INSERT INTO public.resorts_amenities
VALUES (37, 5);
INSERT INTO public.resorts_amenities
VALUES (38, 5);
INSERT INTO public.resorts_amenities
VALUES (39, 5);
INSERT INTO public.resorts_amenities
VALUES (40, 5);
INSERT INTO public.resorts_amenities
VALUES (41, 5);
INSERT INTO public.resorts_amenities
VALUES (42, 5);
INSERT INTO public.resorts_amenities
VALUES (43, 5);
INSERT INTO public.resorts_amenities
VALUES (44, 5);


--
-- TOC entry 4772 (class 0 OID 18483)
-- Dependencies: 251
-- Data for Name: resorts_property_type_property; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.resorts_property_type_property
VALUES (17, 4);
INSERT INTO public.resorts_property_type_property
VALUES (18, 4);
INSERT INTO public.resorts_property_type_property
VALUES (19, 4);
INSERT INTO public.resorts_property_type_property
VALUES (20, 4);
INSERT INTO public.resorts_property_type_property
VALUES (21, 4);
INSERT INTO public.resorts_property_type_property
VALUES (22, 4);
INSERT INTO public.resorts_property_type_property
VALUES (23, 5);
INSERT INTO public.resorts_property_type_property
VALUES (24, 5);
INSERT INTO public.resorts_property_type_property
VALUES (25, 5);
INSERT INTO public.resorts_property_type_property
VALUES (26, 5);
INSERT INTO public.resorts_property_type_property
VALUES (27, 5);
INSERT INTO public.resorts_property_type_property
VALUES (21, 1);
INSERT INTO public.resorts_property_type_property
VALUES (18, 1);
INSERT INTO public.resorts_property_type_property
VALUES (25, 1);
INSERT INTO public.resorts_property_type_property
VALUES (12, 1);
INSERT INTO public.resorts_property_type_property
VALUES (25, 2);
INSERT INTO public.resorts_property_type_property
VALUES (18, 2);
INSERT INTO public.resorts_property_type_property
VALUES (21, 3);


--
-- TOC entry 4776 (class 0 OID 18493)
-- Dependencies: 255
-- Data for Name: subscription; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--


--
-- TOC entry 4778 (class 0 OID 18499)
-- Dependencies: 257
-- Data for Name: subscription_detail; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--


--
-- TOC entry 4780 (class 0 OID 18505)
-- Dependencies: 259
-- Data for Name: subscription_status_event; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

--
-- TOC entry 4786 (class 0 OID 18526)
-- Dependencies: 265
-- Data for Name: transaction_log; Type: TABLE DATA; Schema: public; Owner: hungpd170501
--

INSERT INTO public.transaction_log
VALUES (1, 1, 2, 5, 14.5, 5, '2023/10/02 10:55:50', 0.5);
INSERT INTO public.transaction_log
VALUES (2, 1, 2, 5, 14.5, 5, '2023/10/02 13:47:00', 0.5);
INSERT INTO public.transaction_log
VALUES (3, 1, 2, 0, 19, 5, '2023/10/02 13:47:04', 0.5);
INSERT INTO public.transaction_log
VALUES (4, 3, 1, 495, 14.5, 5, '2023/10/03 15:47:05', 0.5);


