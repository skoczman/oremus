package pl.oazapraga.oremus.app.dto


import java.time.LocalDate

class TestDay {
    Integer id;
    LocalDate date;
    TestDailyReadings dailyReadings;
    String author;
    String title;
    String text;
    String status;
    String languageRev;
    String substantiveRev;
    boolean languageRevComplete;
    boolean substantiveRevComplete;
}