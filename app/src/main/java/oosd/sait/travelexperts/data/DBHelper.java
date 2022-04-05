package oosd.sait.travelexperts.data;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NAME = "TravelExpertsSqlLite.db";
    private static final int VERSION = 1;
    private File databaseFile;
    private Context myContext;

    public DBHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
        databaseFile = Objects.requireNonNull(context).getDatabasePath(NAME);
        myContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Products (" +
                "ProductId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "ProdName TEXT NOT NULL" +
                ")";
        db.execSQL(sql);

        sql = "CREATE TABLE Packages (" +
                "PackageId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "PkgName VARCHAR(50) NOT NULL," +
                "PkgStartDate DATETIME," +
                "PkgEndDate DATETIME," +
                "PkgDesc VARCHAR(50)," +
                "PkgBasePrice DECIMAL(19,4) NOT NULL," +
                "PkgAgencyCommission DECIMAL(19,4)" +
                ")";
         db.execSQL(sql);

         sql = "CREATE TABLE Customers (" +
                 "CustomerId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                 "CustFirstName VARCHAR(25) NOT NULL," +
                 "CustLastName VARCHAR(25) NOT NULL," +
                 "CustAddress VARCHAR(75) NOT NULL," +
                 "CustCity VARCHAR(50) NOT NULL," +
                 "CustProv VARCHAR(2) NOT NULL," +
                 "CustPostal VARCHAR(7) NOT NULL," +
                 "CustCountry VARCHAR(25)," +
                 "CustHomePhone VARCHAR(20)," +
                 "CustBusPhone VARCHAR(20) NOT NULL," +
                 "CustEmail VARCHAR(50) NOT NULL," +
                 "AgentId INT" +
                 ")";
         db.execSQL(sql);

         sql = "CREATE TABLE Bookings (" +
                 "BookingId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                 "BookingDate DATETIME," +
                 "BookingNo VARCHAR(50)," +
                 "TravelerCount DOUBLE," +
                 "CustomerId INT," +
                 "TripTypeId VARCHAR(1)," +
                 "PackageId INT" +
                 ")";
         db.execSQL(sql);

        sql = "INSERT INTO Products VALUES (null, 'Air')";
        db.execSQL(sql);
        sql = "INSERT INTO Products VALUES (null, 'Attractions')";
        db.execSQL(sql);
        sql = "INSERT INTO Products VALUES (null, 'Car rental')";
        db.execSQL(sql);
        sql = "INSERT INTO Products VALUES (null, 'Cruise')";
        db.execSQL(sql);
        sql = "INSERT INTO Products VALUES (null, 'Hotel')";
        db.execSQL(sql);
        sql = "INSERT INTO Products VALUES (null, 'Motor Coach')";
        db.execSQL(sql);
        sql = "INSERT INTO Products VALUES (null, 'Railroad')";
        db.execSQL(sql);
        sql = "INSERT INTO Products VALUES (null, 'Tours')";
        db.execSQL(sql);
        sql = "INSERT INTO Products VALUES (null, 'Travel Insurance')";
        db.execSQL(sql);
        sql = "INSERT INTO Products VALUES (null, 'Yacht/Boat Charters')";
        db.execSQL(sql);

        sql = "INSERT INTO Customers VALUES ('104', 'Laetia', 'Enison', '144-61 87th Ave, NE', 'Calgary', 'AB', 'T2J 6B6', 'Canada', '4032791223', '4032557865', '                                                  ', '4')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('105', 'Angel', 'Moskowitz', '320 John St., NE', 'Calgary', 'AB', 'T2J 7E3', 'Canada', '4032794228', '4036409874', 'amoskowitz@home.com                               ', '3')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('106', 'Judith', 'Olvsade', '29 Elmwood Ave.,', 'Calgary', 'AB', 'T2Z 3M9', 'Canada', '4032795652', '4036861598', 'jolvsade@aol.com                                  ', '1')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('107', 'Catherine', 'Mierzwa', '22-70 41st St.,NW', 'Calgary', 'AB', 'T2Z 2Z9', 'Canada', '4032796878', '4036404563', 'cmierzwa@msn.com                                  ', '5')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('108', 'Judy', 'Sethi', '63 Stratton Hall, SW', 'Calgary', 'AB', 'T1Y 6N4', 'Canada', '4032795111', '4036204789', 'judysehti@home.com                                ', '7')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('109', 'Larry', 'Walter', '38 Bay 26th ST. #2A, NE', 'Calgary', 'AB', 'T2J 6B6', 'Canada', '4032793254', '4032845588', 'lwalter@aol.com                                   ', '4')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('114', 'Winsome', 'Laporte', '268 E.3rd St, SW', 'Calgary', 'AB', 'T1Y 6N4', 'Canada', '4032691125', '4032844565', '                                                  ', '8')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('117', 'Nancy', 'Kuehn', '44-255 9th St., SW', 'Calgary', 'AB', 'T1Y 6N5', 'Canada', '4032693965', '4032843211', '                                                  ', '6')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('118', 'Hiedi', 'Lopez', '168 Rowayton Ave, NW', 'Calgary', 'AB', 'T3A 4ZG', 'Canada', '4032699856', '4035901587', 'hlopez@aol.com                                    ', '5')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('119', 'Mardig', 'Abdou', '160-04 32nd Ave., SW', 'Calgary', 'AB', 'T2P 2G7', 'Canada', '4032691429', '4032251952', '                                                  ', '9')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('120', 'Ralph', 'Alexander', '2054 73rd St, SW', 'Calgary', 'AB', 'T2P 2G7', 'Canada', '4032691634', '4032256547', '                                                  ', '1')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('121', 'Sean', 'Pineda', '3 Salem Rd., NW', 'Calgary', 'AB', 'T2K 3E3', 'Canada', '4032691954', '4036864444', 'spineda@hotmail.com                               ', '3')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('122', 'Julita', 'Lippen', '51-76 VanKleeck St., NW', 'Calgary', 'AB', 'T2K 6C5', 'Canada', '4032551956', '4035901478', 'jlippen@cadvision.co                              ', '4')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('123', 'Pierre', 'Radicola', '322 Atkins Ave., SE', 'Calgary', 'AB', 'T3G 2C6', 'Canada', '4032551677', '4036867536', 'pradicola@home.com                                ', '8')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('127', 'Gary', 'Aung', '135-32 Louis Blvd, NE', 'Calgary', 'AB', 'T2V 2K5', 'Canada', '4032807858', '4037501587', '                                                  ', '9')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('128', 'Jeff', 'Runyan', '109-15 Queens Blvd., NE', 'Calgary', 'AB', 'T2V 2K6', 'Canada', '4032809635', '4036201122', 'jrunyan@aol.com                                   ', '5')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('130', 'Lula', 'Oates', '11A Emory St., NE', 'Calgary', 'AB', 'T3E 3Z4', 'Canada', '4032439653', '4036861587', 'loates@aol.com                                    ', '9')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('133', 'James', 'Reed', '109-621 96th St, NE', 'Calgary', 'AB', 'T3E 4A1', 'Canada', '4032432358', '4037201155', 'jreed@aol.com                                     ', '2')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('135', 'Michelle', 'Masser', '379 Ovington Ave, NE', 'Calgary', 'AB', 'T2J 2S9', 'Canada', '4032441586', '4035908522', 'mmasser@aol.com                                   ', '6')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('138', 'John', 'Smith', '45 Plaza St. West #2D, NE', 'Calgary', 'AB', 'T3E 5C7', 'Canada', '4032449653', '4032837896', 'johnSmith@hotmail.co                              ', '7')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('139', 'Angelo', 'Garshman', '82 Western Ave., NE', 'Calgary', 'AB', 'T3E 5C8', 'Canada', '4032259966', '4032696541', '                                                  ', '3')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('140', 'Derrick', 'Baltazar', '9111 Church Ave. #3N, NE', 'Calgary', 'AB', 'T3E 5C9', 'Canada', '4032255231', '4037502547', '                                                  ', '6')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('141', 'Robert', 'Boyd', '96-04 57th Ave #12A, NE', 'Calgary', 'AB', 'T3E 5C5', 'Canada', '4032255647', '4037509512', '                                                  ', '3')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('142', 'Monica', 'Waldman', '257 Depot Rd., NE', 'Calgary', 'AB', 'T2J 6P3', 'Canada', '4032255629', '4032844566', 'mwaldman@aol.com                                  ', '2')";
        db.execSQL(sql);
        sql = "INSERT INTO Customers VALUES ('143', 'Gerard', 'Biers', '205 19th St., NE', 'Calgary', 'AB', 'T2J 6B6', 'Canada', '4032251952', '4037506578', '                                                  ', '8')";
        db.execSQL(sql);

        sql = "INSERT INTO Packages VALUES ('1', 'Caribbean New Year', '20051225000000', '20060104000000', 'Cruise the Caribbean & Celebrate the New Year.', '4800', '400')";
        db.execSQL(sql);
        sql = "INSERT INTO Packages VALUES ('2', 'Polynesian Paradise', '20051212000000', '20051220000000', '8 Day All Inclusive Hawaiian Vacation', '3000', '310')";
        db.execSQL(sql);
        sql = "INSERT INTO Packages VALUES ('3', 'Asian Expedition', '20060514000000', '20060528000000', 'Airfaire, Hotel and Eco Tour.', '2800', '300')";
        db.execSQL(sql);
        sql = "INSERT INTO Packages VALUES ('4', 'European Vacation', '20051101000000', '20051114000000', 'Euro Tour with Rail Pass and Travel Insurance', '3000', '280')";
        db.execSQL(sql);

        sql = "INSERT INTO Bookings VALUES ('11', '20010131000000', 'DFS3', '1.0', '143', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('15', '20010305000000', 'WDR898', '1.0', '135', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('17', '20010306000000', 'FES3', '1.0', '143', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('34', '20010324000000', 'S935', '2.0', '138', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('46', '20010330000000', 'SKJ329', '2.0', '133', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('49', '20010331000000', 'S943', '2.0', '114', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('52', '20010401000000', 'S934', '2.0', '133', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('55', '20010403000000', 'SDFJ3982', '2.0', '133', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('57', '20010808000000', 'FJKD344', '2.0', '130', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('62', '20011125000000', 'SCR39', '2.0', '130', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('65', '20011217000000', 'HK777', '1.0', '143', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('73', '20020127000000', 'SW34', '1.0', '143', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('79', '20020212000000', 'MKI333', '2.0', '120', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('80', '20020213000000', 'MKI334', '2.0', '122', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('84', '20020215000000', 'KK890', '2.0', '120', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('89', '20020303000000', 'DF344', '1.0', '109', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('98', '20020322000000', 'JI8787', '1.0', '143', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('108', '20020404000000', 'MKI338', '2.0', '138', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('124', '20020429000000', 'SJKDK89', '2.0', '114', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('138', '20020528000000', 'HJK78', '1.0', '109', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('141', '20020601000000', 'KL888', '1.0', '143', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('148', '20020628000000', 'LJ888', '2.0', '133', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('152', '20020815000000', 'WS343', '2.0', '130', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('156', '20020923000000', 'JKKO9', '1.0', '143', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('161', '20020927000000', 'SG4SD', '1.0', '105', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('162', '20020928000000', 'GFRER4', '1.0', '109', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('172', '20021003000000', 'FGFD64', '1.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('187', '19990101000000', 'ZAQ344', '1.0', '109', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('199', '19990115000000', 'JSD39', '1.0', '143', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('204', '19990118000000', 'XVV67', '1.0', '141', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('220', '19990129000000', 'BCV5', '1.0', '127', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('226', '19990219000000', 'DS3DF', '1.0', '143', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('228', '19990220000000', 'KF83', '1.0', '119', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('263', '19990317000000', 'CBB34', '2.0', '120', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('264', '19990318000000', 'SDF890', '1.0', '135', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('266', '19990318000000', 'AZX24', '2.0', '135', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('273', '19990321000000', 'DGG33', '2.0', '122', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('287', '19990415000000', '7898797', '1.0', '141', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('305', '19990421000000', 'XC2', '1.0', '127', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('312', '19990428000000', 'SDSD33', '1.0', '130', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('313', '19990429000000', 'SD46', '1.0', '120', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('322', '19990527000000', 'FJSDKL833', '1.0', '143', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('325', '19990602000000', 'HJJK77', '1.0', '121', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('369', '19990818000000', 'KJ392', '1.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('375', '19990821000000', 'SDJF382', '1.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('381', '19990823000000', 'JDKJF8343', '1.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('382', '19990823000000', 'FDJ93', '1.0', '119', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('384', '19990824000000', 'JHJH7', '1.0', '119', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('409', '19990907000000', 'FD53767', '2.0', '139', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('410', '19990907000000', 'JHK7', '2.0', '140', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('425', '19990912000000', 'FG879', '4.0', '140', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('442', '19990921000000', 'S53423', '1.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('443', '19990921000000', 'T345', '1.0', '119', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('449', '19990923000000', 'RD4EW5', '1.0', '140', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('482', '19991003000000', 'SKFJ32', '1.0', '127', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('484', '19991004000000', 'GDEWR3', '1.0', '106', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('488', '19991005000000', 'JDFS39', '1.0', '106', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('489', '19991005000000', 'SDR54', '1.0', '127', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('510', '19991019000000', 'HKK7', '1.0', '140', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('511', '19991020000000', 'FJK3892', '1.0', '141', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('512', '19991020000000', 'SG444', '1.0', '141', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('517', '19991021000000', 'FSDW2', '1.0', '140', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('529', '19991028000000', 'FKJD32', '1.0', '119', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('546', '19991127000000', 'NKU7', '1.0', '140', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('553', '19991129000000', 'KKU7', '1.0', '109', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('594', '19991206000000', 'HNN77', '1.0', '119', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('596', '19991212000000', 'FDKJ898', '1.0', '106', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('598', '19991213000000', 'FDSK3', '1.0', '121', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('600', '19991214000000', 'ILJ878', '2.0', '106', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('604', '19991215000000', 'KFKESJK5', '1.0', '104', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('605', '19991215000000', 'SDJ89342', '1.0', '140', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('609', '19991216000000', 'KJLK89', '1.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('613', '19991217000000', 'FD2323', '1.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('614', '19991217000000', 'FGG66', '2.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('618', '19991218000000', 'CMFJ39', '1.0', '119', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('622', '19991219000000', 'JJJ77', '1.0', '106', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('631', '19991222000000', 'MM78I879', '1.0', '130', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('665', '20000116000000', 'FDSK83', '1.0', '140', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('676', '20000118000000', 'SJK5', '1.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('677', '20000118000000', 'KJKJ88', '1.0', '140', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('682', '20000119000000', 'GF887', '3.0', '140', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('709', '20000125000000', 'MNHY15', '1.0', '104', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('714', '20000126000000', 'KKJ91', '1.0', '140', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('722', '20000127000000', 'FDJS32', '1.0', '119', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('740', '20000129000000', 'MNHY19', '1.0', '119', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('776', '20000202000000', '345435F', '2.0', '109', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('777', '20000202000000', 'AS54676', '2.0', '143', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('798', '20000204000000', 'A7667900', '1.0', '143', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('805', '20000205000000', '456546DFD', '1.0', '143', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('826', '20000220000000', '62323', '4.0', '128', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('827', '20000220000000', 'D869990', '2.0', '128', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('838', '20000225000000', 'GFF84', '1.0', '141', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('852', '20000229000000', 'GFF79', '1.0', '127', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('857', '20000301000000', 'SFDFSD54', '2.0', '127', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('866', '20000302000000', 'SFDFSD53', '1.0', '127', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('876', '20000303000000', 'SFDFSD55', '1.0', '127', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('880', '20000304000000', 'GFF85', '1.0', '141', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('884', '20000305000000', 'GFF86', '2.0', '141', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('899', '20000315000000', 'QERQ1322', '2.0', '121', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('902', '20000317000000', 'D569767', '2.0', '121', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('910', '20000319000000', 'GFF102', '1.0', '114', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('939', '20000325000000', '86431RT', '2.0', '120', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('943', '20000326000000', '34265Q67L', '2.0', '140', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('960', '20000330000000', 'GFF105', '1.0', '122', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('973', '20000401000000', 'GFF104', '2.0', '133', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('988', '20000404000000', '76584847', '1.0', '139', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('989', '20000404000000', '4656757Q', '1.0', '139', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1001', '20000407000000', '53165616', '2.0', '133', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1034', '20000419000000', 'F789900', '2.0', '105', 'G', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1047', '20000420000000', '234244S', '2.0', '105', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1067', '20000422000000', '4325434RE', '2.0', '117', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1073', '20000423000000', '68798890', '2.0', '117', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1075', '20000423000000', '78755U', '2.0', '123', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1089', '20000424000000', 'T6657D', '2.0', '142', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1105', '20000426000000', '53165765R', '1.0', '119', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1141', '20000504000000', '35653B', '1.0', '120', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1165', '20000525000000', 'LJJ113', '1.0', '127', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1178', '20000530000000', 'LJJ108', '1.0', '118', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1187', '20000614000000', 'R4777FG', '1.0', '143', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1192', '20000628000000', 'LJJ115', '1.0', '109', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1194', '20000629000000', 'LJJ114', '1.0', '141', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1202', '20000714000000', 'LJJ126', '1.0', '135', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1206', '20000715000000', 'LJJ131', '1.0', '121', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1207', '20000715000000', 'FJS3492', '2.0', '135', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1228', '20000801000000', 'LJJ120', '1.0', '106', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1230', '20000803000000', 'LJJ121', '1.0', '107', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1240', '20000814000000', 'FSDFJ357', '1.0', '127', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1248', '20000817000000', 'FSDFJ358', '1.0', '141', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1251', '20000909000000', 'FSDFJ349', '1.0', '130', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1254', '20000912000000', 'KJFKD89', '1.0', '130', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1270', '20001017000000', 'FSD82937', '1.0', '130', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1293', '20001022000000', 'KJLK89234', '1.0', '130', 'L', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1301', '20001121000000', 'FSD82940', '1.0', '127', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1302', '20001122000000', 'FSD82941', '1.0', '141', 'B', null)";
        db.execSQL(sql);
        sql = "INSERT INTO Bookings VALUES ('1303', '20001123000000', 'KJk934', '1.0', '127', 'B', null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE Products");
        onCreate(db);
    }
}