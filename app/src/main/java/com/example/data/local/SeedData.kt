package com.example.data.local

import com.example.data.model.Job
import com.example.data.model.JobSeekerProfile

object SeedData {
    val sampleJobs = listOf(
        Job(
            id = "job_1",
            jobTitle = "Afisa Mauzo na Masoko (Sales Executive)",
            company = "Kilima Consumer Goods Ltd",
            salary = "TZS 800,000 - 1,400,000 / Mwezi",
            location = "Dar es Salaam, Kariakoo",
            jobType = "Muda Wote",
            category = "Uuzaji na Masoko",
            description = "Tunatafuta Afisa Mauzo mwenye shauku na uzoefu wa kusimamia wateja wa jumla, kutafuta masoko mapya ya bidhaa za walaji jijini Dar es Salaam na Pwani, na kufikia malengo ya kila mwezi.",
            requirements = "1. Stashahada au Shahada ya Biashara/Masoko.\n2. Uzoefu usiopungua miaka 2 katika mauzo ya FMCG.\n3. Uwezo wa kuwasiliana vizuri kwa Kiswahili na Kiingereza.\n4. Leseni ya kuendesha pikipiki au gari ni sifa ya ziada.",
            postedBy = "Mkurugenzi wa Rasilimali Watu",
            contactPhone = "+255754123456",
            contactEmail = "ajira@kilimaconsumer.co.tz",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 35 // 35 mins ago
        ),
        Job(
            id = "job_2",
            jobTitle = "Mhasibu Msaidizi (Assistant Accountant)",
            company = "Swahili Logistics & Cargo",
            salary = "TZS 1,200,000 - 1,600,000 / Mwezi",
            location = "Dar es Salaam, Posta",
            jobType = "Muda Wote",
            category = "Uhasibu na Fedha",
            description = "Kazi hii inahusisha kuandaa vitabu vya mahesabu, kushughulikia ankara (invoices), kufanya upatanisho wa kibenki (bank reconciliation), na kuwasilisha ripoti za TRA (VAT, PAYE, SDL) kwa wakati.",
            requirements = "1. Shahada ya Uhasibu / BCom / Finance au CPA(T) ngazi ya kati.\n2. Ujuzi wa programu za Tally Prime, QuickBooks au SAP.\n3. Uelewa mpana wa mifumo ya kodi ya Tanzania (TRA EFD & VFD).\n4. Uadilifu wa hali ya juu.",
            postedBy = "Idara ya Fedha",
            contactPhone = "+255784987654",
            contactEmail = "finance@swahililogistics.co.tz",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 120 // 2 hours ago
        ),
        Job(
            id = "job_3",
            jobTitle = "Dereva wa Malori ya Mzigo Mzito (Heavy Duty Driver)",
            company = "Tanzania Express Freighters",
            salary = "TZS 1,500,000 - 2,200,000 / Mwezi + Posho",
            location = "Dar es Salaam hadi Lusaka / Kigali",
            jobType = "Muda Wote",
            category = "Udereva na Usafirishaji",
            description = "Kuendesha malori ya Scania na Actros ya semi-trailer kusafirisha mizigo toka Bandari ya Dar es Salaam kuelekea nchi jirani za SADC na EAC. Kuzingatia usalama barabarani na utunzaji wa gari.",
            requirements = "1. Leseni ya Udereva Daraja E au C ya Tanzania yenye uzoefu wa miaka 5+.\n2. Cheti cha mafunzo toka Chuo cha Taifa cha Usafirishaji (NIT).\n3. Pasipoti halali ya Kusafiria (EAC / SADC).\n4. Hakuna rekodi ya makosa ya jinai.",
            postedBy = "Meneja wa Usafirishaji",
            contactPhone = "+255762001122",
            contactEmail = "fleet@tzexpress.com",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 300 // 5 hours ago
        ),
        Job(
            id = "job_4",
            jobTitle = "Fundi Umeme wa Majumbani na Viwandani",
            company = "Bongoland Construction & Engineering",
            salary = "TZS 700,000 - 1,100,000 / Mwezi",
            location = "Dodoma Mjini",
            jobType = "Mkataba",
            category = "Ufundi na Ujenzi",
            description = "Kufanya wiring za majengo ya serikali na ya makazi mjini Dodoma, kufunga solar system, kusimamia usalama wa mifumo ya umeme na kutatua hitilafu za umeme wa 3-phase.",
            requirements = "1. Cheti cha VETA Level II au III au FTC ya Umeme.\n2. Uzoefu usiopungua miaka 3 katika ujenzi wa miradi mikubwa.\n3. Uwezo wa kusoma ramani za michoro ya umeme (Electrical layout diagrams).",
            postedBy = "Mhandisi Mkuu wa Mradi",
            contactPhone = "+255713445566",
            contactEmail = "eng@bongolandconst.co.tz",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 700
        ),
        Job(
            id = "job_5",
            jobTitle = "Mpishi Mkuu (Head Chef - Vyakula vya Asili na Kimataifa)",
            company = "Serengeti View Safari Lodge",
            salary = "TZS 1,800,000 - 2,500,000 / Mwezi + Malazi",
            location = "Arusha / Karatu",
            jobType = "Muda Wote",
            category = "Ukarimu na Utalii",
            description = "Kuandaa orodha ya menyu kwa ajili ya wageni wa kitalii, kusimamia jiko, kuhakikisha ubora na usafi wa hali ya juu wa vyakula vya kitanzania na kimataifa.",
            requirements = "1. Cheti au Diploma ya Culinary Arts / Usimamizi wa Hoteli.\n2. Uzoefu wa miaka 4+ kwenye hoteli ya hadhi ya nyota 4 au safari lodge.\n3. Ujuzi mzuri wa lugha ya Kiingereza.",
            postedBy = "Utawala wa Hoteli",
            contactPhone = "+255755889900",
            contactEmail = "jobs@serengetilodge.co.tz",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 1400
        ),
        Job(
            id = "job_6",
            jobTitle = "Mhudumu wa Duka la Dawa (Dispenser / Nurse)",
            company = "Afya Bora Polyclinic & Pharmacy",
            salary = "TZS 650,000 - 900,000 / Mwezi",
            location = "Mwanza, Nyamagana",
            jobType = "Muda Wote",
            category = "Afya na Matibabu",
            description = "Kutoa dawa kwa wateja kulingana na maelekezo ya daktari, kutoa ushauri wa matumizi sahihi ya dawa, na kudhibiti stoo ya dawa.",
            requirements = "1. Cheti cha Dispensing au Uuguzi toka chuo kinachotambuliwa.\n2. Usajili toka Baraza la Famasi Tanzania.\n3. Ukarimu na lugha nzuri kwa wagonjwa.",
            postedBy = "Dkt. Masanja",
            contactPhone = "+255787654321",
            contactEmail = "info@afyaboramwanza.co.tz",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 2000
        )
    )

    val sampleProfiles = listOf(
        JobSeekerProfile(
            id = "prof_1",
            userId = "user_101",
            fullName = "Emmanuel Baraka Mushi",
            title = "Mhasibu na Mchambuzi wa Kodi (CPA-T Candidate)",
            skills = listOf("QuickBooks", "Tally Prime", "TRA EFD/VFD", "Taarifa za Fedha", "Microsoft Excel Advance"),
            experience = "Miaka 4 ya uzoefu katika kampuni ya usambazaji na ukaguzi wa ndani.",
            education = "Shahada ya Uhasibu na Fedha (IAA Arusha)",
            location = "Dar es Salaam, Kinondoni",
            phone = "+255759112233",
            email = "emmanuel.mushi@gmail.com",
            bio = "Mhasibu mchapa kazi na makini katika namba. Nina uwezo mkubwa wa kuweka sawa mahesabu ya kampuni, kupunguza upotevu wa fedha, na kushughulikia kodi zote za TRA bila ucheleweshaji.",
            salaryExpectation = "TZS 1,000,000 - 1,500,000",
            availability = "Tayari Kuanza Mara Moja",
            avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 15
        ),
        JobSeekerProfile(
            id = "prof_2",
            userId = "user_102",
            fullName = "Fatma Said Rashid",
            title = "Mtaalamu wa Mauzo, Masoko ya Kidijitali & Uhusiano wa Wateja",
            skills = listOf("Social Media Marketing", "B2B Sales", "Customer Care", "Canva Design", "Usimamizi wa Wateja"),
            experience = "Miaka 3 nikisimamia kurasa za biashara na kuongeza mauzo kwa 45%.",
            education = "Diploma ya Uhusiano wa Umma na Masoko (Tanzania School of Journalism)",
            location = "Dar es Salaam, Ilala",
            phone = "+255714223344",
            email = "fatma.rashid@yahoo.com",
            bio = "Nina shauku kubwa ya kukuza chapa (brand) na kuvutia wateja wapya kupitia mbinu za kisasa za kidijitali na mauzo ya moja kwa moja. Mawasiliano yangu ni ya kiwango cha juu.",
            salaryExpectation = "TZS 750,000 - 1,200,000",
            availability = "Ilani ya Wiki 1",
            avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=300",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 85
        ),
        JobSeekerProfile(
            id = "prof_3",
            userId = "user_103",
            fullName = "Rashid Juma Mwalimu",
            title = "Dereva Mzoefu wa Magari Madogo na Malori (Class C/E)",
            skills = listOf("Leseni Daraja C & E", "Ufundi Gari wa Msingi", "NIT Certificate", "Ufahamu wa Barabara za Tanzania", "Nidhamu ya Juu"),
            experience = "Miaka 6 ya kuendesha mabasi na malori mikoani bila ajali.",
            education = "Cheti cha Udereva wa Kulinda (Defensive Driving) - NIT",
            location = "Morogoro / Dar es Salaam",
            phone = "+255768334455",
            email = "rashid.mwalimu@outlook.com",
            bio = "Dereva mwaminifu asiye na kashfa yoyote ya upotevu wa mizigo au ajali. Ninatunza gari kwa uangalifu mkubwa na ninafahamu njia zote za Tanzania na nchi jirani.",
            salaryExpectation = "TZS 900,000 - 1,400,000",
            availability = "Tayari Kuanza Mara Moja",
            avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=300",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 190
        ),
        JobSeekerProfile(
            id = "prof_4",
            userId = "user_104",
            fullName = "Asha Ally Mwita",
            title = "Mtaalamu wa Teknolojia ya Habari & Web Developer",
            skills = listOf("Kotlin", "Android Development", "WordPress", "IT Support", "Networking LAN/WAN"),
            experience = "Miaka 2 nikitengeneza mifumo ya wavuti na kutatua matatizo ya vifaa vya IT maofisini.",
            education = "Shahada ya Sayansi ya Kompyuta (UDSM)",
            location = "Dar es Salaam, Ubungo",
            phone = "+255745778899",
            email = "asha.mwita@gmail.com",
            bio = "Mbunifu wa programu mwenye uwezo wa kujifunza haraka na kutatua changamoto za mifumo ya ofisi. Natafuta nafasi ya kudumu au mkataba katika kampuni inayokua kwa kasi.",
            salaryExpectation = "TZS 1,200,000 - 1,800,000",
            availability = "Tayari Kuanza",
            avatarUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=300",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 420
        ),
        JobSeekerProfile(
            id = "prof_5",
            userId = "user_105",
            fullName = "Gabriel Peter Shayo",
            title = "Mhandisi wa Ujenzi na Usimamizi wa Miradi (Site Engineer)",
            skills = listOf("AutoCAD", "ArchiCAD", "Usimamizi wa Mafundi", "Makadirio ya Vifaa (BOQ)", "Safety on Site"),
            experience = "Miaka 5 akisimamia ujenzi wa majengo ya ghorofa na barabara za lami.",
            education = "Shahada ya Uhandisi wa Ujenzi (DIT)",
            location = "Arusha Mjini",
            phone = "+255782114477",
            email = "gabriel.shayo@engineer.com",
            bio = "Site Engineer mwenye usimamizi thabiti, ninahakikisha miradi inakamilika kwa kiwango bora na kwa wakati uliopangwa bila kuvuka bajeti.",
            salaryExpectation = "TZS 1,800,000 - 2,500,000",
            availability = "Ilani ya Siku 14",
            avatarUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=300",
            timestamp = System.currentTimeMillis() - 1000 * 60 * 950
        )
    )

    val sampleNotifications = listOf(
        com.example.data.model.AppNotification(
            id = "notif_1",
            title = "Kazi Mpya Imetangazwa!",
            message = "Afisa Mauzo na Masoko (Sales Executive) - Kilima Consumer Goods Ltd jijini Dar es Salaam.",
            timeAgo = "Dakika 35 zilizopita",
            type = "job"
        ),
        com.example.data.model.AppNotification(
            id = "notif_2",
            title = "Mtafuta Kazi Mpya Amejiunga",
            message = "Emmanuel Baraka Mushi (Mhasibu na Mchambuzi wa Kodi) yuko tayari kuajiriwa sasa.",
            timeAgo = "Saa 1 lililopita",
            type = "profile"
        ),
        com.example.data.model.AppNotification(
            id = "notif_3",
            title = "Karibu ClipZone Ajira!",
            message = "Tafadhali kamilisha wasifu wako ili waajiri waweze kukupata kwa urahisi.",
            timeAgo = "Masaa 4 yaliyopita",
            type = "system"
        )
    )
}
