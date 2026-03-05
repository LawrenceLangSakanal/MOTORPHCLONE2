package com.payroll;

import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeLoader – hardcoded employee list matching EmployeeData.csv.
 * All passwords set to "admin" for uniform access.
 */
public class EmployeeLoader {

    public static List<RegularEmployee> loadEmployeeData() {
        List<RegularEmployee> employees = new ArrayList<>();

        // ── 10000 ─────────────────────────────────────────────────────────────
        RegularEmployee e10000 = new RegularEmployee(
                10000, "admin", "Admin", "Super",
                "1/1/2024",
                "7 Jupiter Avenue cor. F. Sandoval Jr., Bagong Nayon, Quezon City",
                "028-911-5071",
                "12-3456789-0", "12-321413542-6", "123-456-788-990", "1234-5678-9012",
                "IT Operations and Systems", "Lim, Antonio",
                90000, 1500, 2000, 1000, 45000, 535.71
        );
        e10000.setSssDeduction(1125.00);
        e10000.setPhilhealthDeduction(900.00);
        e10000.setPagibigDeduction(100.00);
        e10000.setTaxDeduction(17195.40);
        employees.add(e10000);

        // ── 10001 ─────────────────────────────────────────────────────────────
        RegularEmployee e10001 = new RegularEmployee(
                10001, "admin", "Garcia", "Manuel III",
                "10/11/1983",
                "Valero Carpark Building Valero Street 1227, Makati City",
                "966-860-270",
                "44-4506057-3", "82-012685395-1", "442-605-657-000", "6912-9533-0870",
                "Chief Executive Officer", "Garcia, Manuel III",
                90000, 1500, 2000, 1000, 45000, 535.71
        );
        e10001.setSssDeduction(1125.00);
        e10001.setPhilhealthDeduction(900.00);
        e10001.setPagibigDeduction(100.00);
        e10001.setTaxDeduction(17195.40);
        employees.add(e10001);

        // ── 10002 ─────────────────────────────────────────────────────────────
        RegularEmployee e10002 = new RegularEmployee(
                10002, "admin", "Lim", "Antonio",
                "06/19/1988",
                "San Antonio De Padua 2, Block 1 Lot 8 and 2, Dasmarinas, Cavite",
                "171-867-411",
                "52-2061274-9", "33-173564633-8", "683-102-776-000", "6639-0499-5411",
                "Chief Operating Officer", "Garcia, Manuel III",
                60000, 1500, 2000, 1000, 30000, 357.14
        );
        e10002.setSssDeduction(1125.00);
        e10002.setPhilhealthDeduction(900.00);
        e10002.setPagibigDeduction(100.00);
        e10002.setTaxDeduction(8635.50);
        employees.add(e10002);

        // ── 10003 ─────────────────────────────────────────────────────────────
        RegularEmployee e10003 = new RegularEmployee(
                10003, "admin", "Aquino", "Bianca Sofia",
                "08/04/1989",
                "Rm. 402 4/F Jiao Building Timog Avenue Cor. Quezon Avenue 1100, Quezon City",
                "966-889-370",
                "30-8870406-2", "17-745118966-5", "971-711-280-000", "1715-1977-3969",
                "Chief Finance Officer", "Garcia, Manuel III",
                60000, 1500, 2000, 1000, 30000, 357.14
        );
        e10003.setSssDeduction(1125.00);
        e10003.setPhilhealthDeduction(900.00);
        e10003.setPagibigDeduction(100.00);
        e10003.setTaxDeduction(8635.50);
        employees.add(e10003);

        // ── 10004 ─────────────────────────────────────────────────────────────
        RegularEmployee e10004 = new RegularEmployee(
                10004, "admin", "Reyes", "Isabella",
                "06/16/1994",
                "460 Solanda Street Intramuros 1000, Manila",
                "786-868-477",
                "40-2511815-0", "34-191141125-4", "876-809-437-000", "4169-4677-6041",
                "Chief Marketing Officer", "Garcia, Manuel III",
                60000, 1500, 2000, 1000, 30000, 357.14
        );
        e10004.setSssDeduction(1125.00);
        e10004.setPhilhealthDeduction(900.00);
        e10004.setPagibigDeduction(100.00);
        e10004.setTaxDeduction(8635.50);
        employees.add(e10004);

        // ── 10005 ─────────────────────────────────────────────────────────────
        RegularEmployee e10005 = new RegularEmployee(
                10005, "admin", "Hernandez", "Eduard",
                "09/23/1989",
                "National Highway, Gingoog, Misamis Occidental",
                "088-861-012",
                "50-5577638-1", "95-743619181-2", "031-702-374-000", "9523-4722-2457",
                "IT Operations and Systems", "Lim, Antonio",
                52670, 1500, 1000, 1000, 26335, 313.51
        );
        e10005.setSssDeduction(1125.00);
        e10005.setPhilhealthDeduction(790.05);
        e10005.setPagibigDeduction(100.00);
        e10005.setTaxDeduction(6830.49);
        employees.add(e10005);

        // ── 10006 ─────────────────────────────────────────────────────────────
        RegularEmployee e10006 = new RegularEmployee(
                10006, "admin", "Villanueva", "Andrea Mae",
                "02/14/1988",
                "17/85 Stracke Via Suite 042, Poblacion, Las Pinas 4783 Dinagat Islands",
                "918-621-603",
                "49-1632020-8", "38-218945314-5", "317-674-022-000", "4410-9336-9646",
                "HR Manager", "Lim, Antonio",
                52670, 1500, 1000, 1000, 26335, 313.51
        );
        e10006.setSssDeduction(1125.00);
        e10006.setPhilhealthDeduction(790.05);
        e10006.setPagibigDeduction(100.00);
        e10006.setTaxDeduction(6830.49);
        employees.add(e10006);

        // ── 10007 ─────────────────────────────────────────────────────────────
        RegularEmployee e10007 = new RegularEmployee(
                10007, "admin", "San Jose", "Brad",
                "03/15/1996",
                "99 Strosin Hills, Poblacion, Bislig 5340 Tawi-Tawi",
                "797-009-261",
                "40-2400714-1", "23-919292693-9", "672-474-690-000", "2108-5020-9964",
                "HR Team Leader", "Villanueva, Andrea Mae",
                42975, 1500, 800, 800, 21488, 255.80
        );
        e10007.setSssDeduction(1125.00);
        e10007.setPhilhealthDeduction(644.63);
        e10007.setPagibigDeduction(100.00);
        e10007.setTaxDeduction(4443.09);
        employees.add(e10007);

        // ── 10008 ─────────────────────────────────────────────────────────────
        RegularEmployee e10008 = new RegularEmployee(
                10008, "admin", "Romualdez", "Alice",
                "05/14/1992",
                "12A/33 Upton Isle Apt. 420, Roxas City 1814 Surigao del Norte",
                "983-606-799",
                "55-4476527-2", "54-565264023-2", "888-572-294-000", "2113-8555-6888",
                "HR Rank and File", "San Jose, Brad",
                22500, 1500, 500, 500, 11250, 133.93
        );
        e10008.setSssDeduction(1012.50);
        e10008.setPhilhealthDeduction(337.50);
        e10008.setPagibigDeduction(100.00);
        e10008.setTaxDeduction(43.40);
        employees.add(e10008);

        // ── 10009 ─────────────────────────────────────────────────────────────
        RegularEmployee e10009 = new RegularEmployee(
                10009, "admin", "Atienza", "Rosie",
                "09/24/1948",
                "90A Dibbert Terrace Apt. 190, San Lorenzo 6056 Davao del Norte",
                "266-036-427",
                "41-0644692-3", "70-898823485-3", "604-997-793-000", "2601-0773-2354",
                "HR Rank and File", "San Jose, Brad",
                22500, 1500, 500, 500, 11250, 133.93
        );
        e10009.setSssDeduction(1012.50);
        e10009.setPhilhealthDeduction(337.50);
        e10009.setPagibigDeduction(100.00);
        e10009.setTaxDeduction(43.40);
        employees.add(e10009);

        // ── 10010 ─────────────────────────────────────────────────────────────
        RegularEmployee e10010 = new RegularEmployee(
                10010, "admin", "Alvaro", "Roderick",
                "03/30/1988",
                "#284 T. Morato corner, Scout Rallos Street, Quezon City",
                "053-381-386",
                "64-7605054-4", "57-811485319-4", "525-420-419-000", "7992-5409-5212",
                "Accounting Head", "Aquino, Bianca Sofia",
                52670, 1500, 1000, 1000, 26335, 313.51
        );
        e10010.setSssDeduction(1125.00);
        e10010.setPhilhealthDeduction(790.05);
        e10010.setPagibigDeduction(100.00);
        e10010.setTaxDeduction(6830.49);
        employees.add(e10010);

        // ── 10011 ─────────────────────────────────────────────────────────────
        RegularEmployee e10011 = new RegularEmployee(
                10011, "admin", "Salcedo", "Anthony",
                "09/14/1993",
                "93/54 Shanahan Alley Apt. 183, Santo Tomas 1572 Masbate",
                "070-766-300",
                "26-9647608-3", "12-644531565-1", "210-805-911-000", "2180-0247-3454",
                "Payroll Manager", "Alvaro, Roderick",
                50825, 1500, 1000, 1000, 25413, 302.53
        );
        e10011.setSssDeduction(1125.00);
        e10011.setPhilhealthDeduction(762.38);
        e10011.setPagibigDeduction(100.00);
        e10011.setTaxDeduction(6376.16);
        employees.add(e10011);

        // ── 10012 ─────────────────────────────────────────────────────────────
        RegularEmployee e10012 = new RegularEmployee(
                10012, "admin", "Lopez", "Josie",
                "01/14/1987",
                "49 Springs Apt. 266, Poblacion, Taguig 3200 Occidental Mindoro",
                "478-355-427",
                "44-8563448-3", "43-170901101-2", "218-489-737-000", "1130-7129-3354",
                "Payroll Team Leader", "Salcedo, Anthony",
                38475, 1500, 800, 800, 19238, 229.02
        );
        e10012.setSssDeduction(1125.00);
        e10012.setPhilhealthDeduction(577.13);
        e10012.setPagibigDeduction(100.00);
        e10012.setTaxDeduction(3334.97);
        employees.add(e10012);

        // ── 10013 ─────────────────────────────────────────────────────────────
        RegularEmployee e10013 = new RegularEmployee(
                10013, "admin", "Farala", "Martha",
                "01/11/1942",
                "42/25 Sawayn Stream, Ubay 1208 Zamboanga del Norte",
                "329-034-366",
                "45-5656375-0", "23-369389724-7", "210-835-851-000", "6311-3028-3546",
                "Payroll Rank and File", "Salcedo, Anthony",
                24000, 1500, 500, 500, 12000, 142.86
        );
        e10013.setSssDeduction(1080.00);
        e10013.setPhilhealthDeduction(360.00);
        e10013.setPagibigDeduction(100.00);
        e10013.setTaxDeduction(325.40);
        employees.add(e10013);

        // ── 10014 ─────────────────────────────────────────────────────────────
        RegularEmployee e10014 = new RegularEmployee(
                10014, "admin", "Martinez", "Leila",
                "07/11/1970",
                "37/46 Kulas Roads, Maragondon 0962 Quirino",
                "877-110-749",
                "27-2090996-4", "51-574105749-6", "275-792-513-000", "1012-0544-5886",
                "Payroll Rank and File", "Salcedo, Anthony",
                24000, 1500, 500, 500, 12000, 142.86
        );
        e10014.setSssDeduction(1080.00);
        e10014.setPhilhealthDeduction(360.00);
        e10014.setPagibigDeduction(100.00);
        e10014.setTaxDeduction(325.40);
        employees.add(e10014);

        // ── 10015 ─────────────────────────────────────────────────────────────
        RegularEmployee e10015 = new RegularEmployee(
                10015, "admin", "Romualdez", "Fredrick",
                "03/10/1985",
                "22A/52 Lubowitz Meadows, Pililla 4895 Zambales",
                "023-079-009",
                "26-8768374-1", "30-836686005-9", "598-065-761-000", "2230-5770-7853",
                "Account Manager", "Lim, Antonio",
                53500, 1500, 1000, 1000, 26750, 318.45
        );
        e10015.setSssDeduction(1125.00);
        e10015.setPhilhealthDeduction(802.50);
        e10015.setPagibigDeduction(100.00);
        e10015.setTaxDeduction(7035.13);
        employees.add(e10015);

        // ── 10016 ─────────────────────────────────────────────────────────────
        RegularEmployee e10016 = new RegularEmployee(
                10016, "admin", "Mata", "Christian",
                "10/21/1987",
                "90 O'Keefe Spur Apt. 379, Catigbian 2772 Sulu",
                "783-776-744",
                "49-2959312-6", "82-418796196-2", "103-100-522-000", "6310-5285-3464",
                "Account Team Leader", "Romualdez, Fredrick",
                42975, 1500, 800, 800, 21488, 255.80
        );
        e10016.setSssDeduction(1125.00);
        e10016.setPhilhealthDeduction(644.63);
        e10016.setPagibigDeduction(100.00);
        e10016.setTaxDeduction(4443.09);
        employees.add(e10016);

        // ── 10017 ─────────────────────────────────────────────────────────────
        RegularEmployee e10017 = new RegularEmployee(
                10017, "admin", "De Leon", "Selena",
                "02/20/1975",
                "89A Armstrong Trace, Compostela 7874 Maguindanao",
                "975-432-139",
                "27-2090208-8", "58-727246993-8", "482-259-498-000", "7190-0760-8464",
                "Account Team Leader", "Romualdez, Fredrick",
                41850, 1500, 800, 800, 20925, 249.11
        );
        e10017.setSssDeduction(1125.00);
        e10017.setPhilhealthDeduction(627.75);
        e10017.setPagibigDeduction(100.00);
        e10017.setTaxDeduction(4166.31);
        employees.add(e10017);

        // ── 10018 ─────────────────────────────────────────────────────────────
        RegularEmployee e10018 = new RegularEmployee(
                10018, "admin", "San Jose", "Allison",
                "06/24/1986",
                "08 Grant Drive Suite 406, Poblacion, Iloilo City 9186 La Union",
                "179-075-129",
                "45-3251383-0", "74-514845952-1", "121-203-336-000", "1149-0185-9343",
                "Account Rank and File", "Mata, Christian",
                22500, 1500, 500, 500, 11250, 133.93
        );
        e10018.setSssDeduction(1012.50);
        e10018.setPhilhealthDeduction(337.50);
        e10018.setPagibigDeduction(100.00);
        e10018.setTaxDeduction(43.40);
        employees.add(e10018);

        // ── 10019 ─────────────────────────────────────────────────────────────
        RegularEmployee e10019 = new RegularEmployee(
                10019, "admin", "Rosario", "Cydney",
                "10/06/1996",
                "93A/21 Berge Points, Tapaz 2180 Quezon",
                "868-819-912",
                "49-1629900-2", "57-925343549-9", "122-244-511-000", "2651-0435-8643",
                "Account Rank and File", "Mata, Christian",
                22500, 1500, 500, 500, 11250, 133.93
        );
        e10019.setSssDeduction(1012.50);
        e10019.setPhilhealthDeduction(337.50);
        e10019.setPagibigDeduction(100.00);
        e10019.setTaxDeduction(43.40);
        employees.add(e10019);

        // ── 10020 ─────────────────────────────────────────────────────────────
        RegularEmployee e10020 = new RegularEmployee(
                10020, "admin", "Bautista", "Mark",
                "02/12/1991",
                "65 Murphy Center Suite 094, Poblacion, Palayan 5636 Quirino",
                "683-725-348",
                "49-1647342-5", "39-966515713-5", "273-970-941-000", "2600-5458-5575",
                "Account Rank and File", "Mata, Christian",
                23250, 1500, 500, 500, 11625, 138.39
        );
        e10020.setSssDeduction(1046.25);
        e10020.setPhilhealthDeduction(348.75);
        e10020.setPagibigDeduction(100.00);
        e10020.setTaxDeduction(180.85);
        employees.add(e10020);

        // ── 10021 ─────────────────────────────────────────────────────────────
        RegularEmployee e10021 = new RegularEmployee(
                10021, "admin", "Lazaro", "Darlene",
                "11/25/1985",
                "47A/94 Larkin Plaza Apt. 179, Poblacion, Caloocan 2751 Quirino",
                "740-721-558",
                "45-5617168-2", "60-638691751-0", "354-650-951-000", "1049-0770-8845",
                "Account Rank and File", "Mata, Christian",
                23250, 1500, 500, 500, 11625, 138.39
        );
        e10021.setSssDeduction(1046.25);
        e10021.setPhilhealthDeduction(348.75);
        e10021.setPagibigDeduction(100.00);
        e10021.setTaxDeduction(180.85);
        employees.add(e10021);

        // ── 10022 ─────────────────────────────────────────────────────────────
        RegularEmployee e10022 = new RegularEmployee(
                10022, "admin", "Delos Santos", "Kolby",
                "02/26/1980",
                "06A Gulgowski Extensions, Bongabon 6085 Zamboanga del Sur",
                "739-443-033",
                "52-0109570-6", "35-745127127-4", "187-500-345-000", "1130-1798-8667",
                "Account Rank and File", "Mata, Christian",
                24000, 1500, 500, 500, 12000, 142.86
        );
        e10022.setSssDeduction(1080.00);
        e10022.setPhilhealthDeduction(360.00);
        e10022.setPagibigDeduction(100.00);
        e10022.setTaxDeduction(325.40);
        employees.add(e10022);

        // ── 10023 ─────────────────────────────────────────────────────────────
        RegularEmployee e10023 = new RegularEmployee(
                10023, "admin", "Santos", "Vella",
                "12/31/1983",
                "99A Padberg Spring, Poblacion, Mabalacat 3959 Lanao del Sur",
                "955-879-269",
                "52-9883524-3", "54-867048288-5", "101-558-994-000", "3600-2810-4576",
                "Account Rank and File", "Mata, Christian",
                22500, 1500, 500, 500, 11250, 133.93
        );
        e10023.setSssDeduction(1012.50);
        e10023.setPhilhealthDeduction(337.50);
        e10023.setPagibigDeduction(100.00);
        e10023.setTaxDeduction(43.40);
        employees.add(e10023);

        // ── 10024 ─────────────────────────────────────────────────────────────
        RegularEmployee e10024 = new RegularEmployee(
                10024, "admin", "Del Rosario", "Tomas",
                "12/18/1978",
                "80A/48 Ledner Ridges, Poblacion, Kabankalan 8870 Marinduque",
                "882-550-989",
                "45-5866331-6", "95-390153999-5", "560-735-732-000", "9131-0864-9964",
                "Account Rank and File", "Mata, Christian",
                22500, 1500, 500, 500, 11250, 133.93
        );
        e10024.setSssDeduction(1012.50);
        e10024.setPhilhealthDeduction(337.50);
        e10024.setPagibigDeduction(100.00);
        e10024.setTaxDeduction(43.40);
        employees.add(e10024);

        // ── 10025 ─────────────────────────────────────────────────────────────
        RegularEmployee e10025 = new RegularEmployee(
                10025, "admin", "Tolentino", "Jacklyn",
                "05/19/1984",
                "96/48 Watsica Flats Suite 734, Poblacion, Malolos 1844 Ifugao",
                "675-757-366",
                "47-1692793-0", "75-380065411-4", "841-177-857-000", "2105-4666-1243",
                "Account Rank and File", "De Leon, Selena",
                24000, 1500, 500, 500, 12000, 142.86
        );
        e10025.setSssDeduction(1080.00);
        e10025.setPhilhealthDeduction(360.00);
        e10025.setPagibigDeduction(100.00);
        e10025.setTaxDeduction(325.40);
        employees.add(e10025);

        // ── 10026 ─────────────────────────────────────────────────────────────
        RegularEmployee e10026 = new RegularEmployee(
                10026, "admin", "Gutierrez", "Percival",
                "12/18/1970",
                "58A Wilderman Walks, Poblacion, Digos 5822 Davao del Sur",
                "512-899-876",
                "40-9504657-8", "79-763938226-5", "502-995-671-000", "2108-9709-5686",
                "Account Rank and File", "De Leon, Selena",
                24750, 1500, 500, 500, 12375, 147.32
        );
        e10026.setSssDeduction(1113.75);
        e10026.setPhilhealthDeduction(371.25);
        e10026.setPagibigDeduction(100.00);
        e10026.setTaxDeduction(466.35);
        employees.add(e10026);

        // ── 10027 ─────────────────────────────────────────────────────────────
        RegularEmployee e10027 = new RegularEmployee(
                10027, "admin", "Manalaysay", "Garfield",
                "08/28/1986",
                "60 Goyette Valley Suite 219, Poblacion, Tabuk 3159 Lanao del Sur",
                "948-628-136",
                "45-3298166-4", "81-090928626-4", "336-676-445-000", "2112-7447-6563",
                "Account Rank and File", "De Leon, Selena",
                24750, 1500, 500, 500, 12375, 147.32
        );
        e10027.setSssDeduction(1113.75);
        e10027.setPhilhealthDeduction(371.25);
        e10027.setPagibigDeduction(100.00);
        e10027.setTaxDeduction(466.35);
        employees.add(e10027);

        // ── 10028 ─────────────────────────────────────────────────────────────
        RegularEmployee e10028 = new RegularEmployee(
                10028, "admin", "Villegas", "Lizeth",
                "12/12/1981",
                "66/77 Mann Views, Luisiana 1263 Dinagat Islands",
                "332-372-215",
                "40-2400719-4", "93-438965299-4", "210-395-397-000", "1222-3807-7997",
                "Account Rank and File", "De Leon, Selena",
                24000, 1500, 500, 500, 12000, 142.86
        );
        e10028.setSssDeduction(1080.00);
        e10028.setPhilhealthDeduction(360.00);
        e10028.setPagibigDeduction(100.00);
        e10028.setTaxDeduction(325.40);
        employees.add(e10028);

        // ── 10029 ─────────────────────────────────────────────────────────────
        RegularEmployee e10029 = new RegularEmployee(
                10029, "admin", "Ramos", "Carol",
                "08/20/1978",
                "72/70 Stamm Spurs, Bustos 4550 Iloilo",
                "250-700-389",
                "60-1152206-4", "35-183046974-4", "395-032-717-000", "2121-4189-3454",
                "Account Rank and File", "De Leon, Selena",
                22500, 1500, 500, 500, 11250, 133.93
        );
        e10029.setSssDeduction(1012.50);
        e10029.setPhilhealthDeduction(337.50);
        e10029.setPagibigDeduction(100.00);
        e10029.setTaxDeduction(43.40);
        employees.add(e10029);

        // ── 10030 ─────────────────────────────────────────────────────────────
        RegularEmployee e10030 = new RegularEmployee(
                10030, "admin", "Maceda", "Emelia",
                "04/14/1973",
                "50A/83 Bahringer Oval Suite 145, Kiamba 7688 Nueva Ecija",
                "973-358-041",
                "54-1331005-0", "46-508789411-2", "215-973-013-000", "5150-1257-9765",
                "Account Rank and File", "De Leon, Selena",
                22500, 1500, 500, 500, 11250, 133.93
        );
        e10030.setSssDeduction(1012.50);
        e10030.setPhilhealthDeduction(337.50);
        e10030.setPagibigDeduction(100.00);
        e10030.setTaxDeduction(43.40);
        employees.add(e10030);

        // ── 10031 ─────────────────────────────────────────────────────────────
        RegularEmployee e10031 = new RegularEmployee(
                10031, "admin", "Aguilar", "Delia",
                "01/27/1989",
                "95 Cremin Junction, Surallah 2809 Cotabato",
                "529-705-439",
                "52-1859253-1", "13-645130306-8", "599-312-588-000", "1100-1881-3465",
                "Account Rank and File", "De Leon, Selena",
                22500, 1500, 500, 500, 11250, 133.93
        );
        e10031.setSssDeduction(1012.50);
        e10031.setPhilhealthDeduction(337.50);
        e10031.setPagibigDeduction(100.00);
        e10031.setTaxDeduction(43.40);
        employees.add(e10031);

        // ── 10032 ─────────────────────────────────────────────────────────────
        RegularEmployee e10032 = new RegularEmployee(
                10032, "admin", "Castro", "John Rafael",
                "02/09/1992",
                "Hi-way, Yati, Liloan Cebu",
                "332-424-955",
                "26-7145133-4", "60-164490240-2", "404-768-309-000", "6977-6406-9311",
                "Sales & Marketing", "Reyes, Isabella",
                52670, 1500, 1000, 1000, 26335, 313.51
        );
        e10032.setSssDeduction(1125.00);
        e10032.setPhilhealthDeduction(790.05);
        e10032.setPagibigDeduction(100.00);
        e10032.setTaxDeduction(6830.49);
        employees.add(e10032);

        // ── 10033 ─────────────────────────────────────────────────────────────
        RegularEmployee e10033 = new RegularEmployee(
                10033, "admin", "Martinez", "Carlos Ian",
                "11/16/1990",
                "Bulala, Camalaniugan",
                "078-854-208",
                "11-5062972-7", "38-068538721-2", "256-436-296-000", "9933-7296-3726",
                "Supply Chain and Logistics", "Reyes, Isabella",
                52670, 1500, 1000, 1000, 26335, 313.51
        );
        e10033.setSssDeduction(1125.00);
        e10033.setPhilhealthDeduction(790.05);
        e10033.setPagibigDeduction(100.00);
        e10033.setTaxDeduction(6830.49);
        employees.add(e10033);

        // ── 10034 ─────────────────────────────────────────────────────────────
        RegularEmployee e10034 = new RegularEmployee(
                10034, "admin", "Santos", "Beatriz",
                "08/07/1990",
                "Agapita Building, Metro Manila 10000",
                "526-639-511",
                "20-2987501-5", "91-846005007-7", "911-529-713-000", "8740-4225-9378",
                "Customer Service", "Villanueva, Andrea Mae",
                52670, 1500, 1000, 1000, 26335, 313.51
        );
        e10034.setSssDeduction(1125.00);
        e10034.setPhilhealthDeduction(790.05);
        e10034.setPagibigDeduction(100.00);
        e10034.setTaxDeduction(6830.49);
        employees.add(e10034);

        // ── 10035 ─────────────────────────────────────────────────────────────
        RegularEmployee e10035 = new RegularEmployee(
                10035, "admin", "Ongo", "James",
                "05/01/2000",
                "Bacolod City",
                "0909090909",
                "01-0101010-1", "67-676767676-7", "454-545-454-545", "2323-2323-2323",
                "IT Operations and Systems", "Admin, Super",
                20000, 500, 500, 500, 10000, 119.05
        );
        e10035.setSssDeduction(900.00);
        e10035.setPhilhealthDeduction(300.00);
        e10035.setPagibigDeduction(100.00);
        e10035.setTaxDeduction(0.00);
        employees.add(e10035);

        return employees;
    }
}
