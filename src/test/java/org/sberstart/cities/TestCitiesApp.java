package org.sberstart.cities;

import org.junit.jupiter.api.Test;
import org.sberstart.cities.exceptions.BadCitiesFileNameException;
import org.sberstart.cities.exceptions.BadCityLineInputException;
import org.sberstart.cities.parser.CityFileParser;
import org.sberstart.cities.service.CityService;
import org.sberstart.cities.sorting.SortByDistrictAndNameAscNotIgnoreCase;
import org.sberstart.cities.sorting.SortByNameAscIgnoreCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCitiesApp {

    private static final CityFileParser CITY_FILE_PARSER_EMPTY = new CityFileParser(new CityRepository());

    @Test
    public void testBadFileInput() {
        assertThrows(BadCitiesFileNameException.class, () -> CITY_FILE_PARSER_EMPTY.parseObjectsFromInputFile(null));
        assertThrows(BadCitiesFileNameException.class, () -> CITY_FILE_PARSER_EMPTY.parseObjectsFromInputFile(""));
        assertThrows(BadCitiesFileNameException.class, () -> CITY_FILE_PARSER_EMPTY.parseObjectsFromInputFile("some filename"));
    }

    @Test
    public void testBadCityLineInFile() {
        assertThrows(BadCityLineInputException.class, () -> CITY_FILE_PARSER_EMPTY.parseObjectsFromInputFile("test/badId.txt"));
        assertThrows(BadCityLineInputException.class, () -> CITY_FILE_PARSER_EMPTY.parseObjectsFromInputFile("test/badName.txt"));
        assertThrows(BadCityLineInputException.class, () -> CITY_FILE_PARSER_EMPTY.parseObjectsFromInputFile("test/badRegion.txt"));
        assertThrows(BadCityLineInputException.class, () -> CITY_FILE_PARSER_EMPTY.parseObjectsFromInputFile("test/badDistrict.txt"));
        assertThrows(BadCityLineInputException.class, () -> CITY_FILE_PARSER_EMPTY.parseObjectsFromInputFile("test/badPopulation.txt"));
        assertThrows(BadCityLineInputException.class, () -> CITY_FILE_PARSER_EMPTY.parseObjectsFromInputFile("test/badFoundation.txt"));
    }

    @Test
    public void testCorrectOutput() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        CityService service = new CityService();
        CityFileParser cityFileParser = new CityFileParser(service.getRepository());

        cityFileParser.parseObjectsFromInputFile("cities.txt");
        service.printAllCities();

        String expected = "City{name='Адыгейск', region='Адыгея', district='Южный', population=12248, foundation=1973}\n" +
                "City{name='Майкоп', region='Адыгея', district='Южный', population=144246, foundation=1857}\n" +
                "City{name='Горно-Алтайск', region='Алтай', district='Сибирский', population=56928, foundation=1830}\n" +
                "City{name='Абаза', region='Хакасия', district='Сибирский', population=17111, foundation=1867}\n" +
                "City{name='Абакан', region='Хакасия', district='Сибирский', population=165183, foundation=1931}\n" +
                "City{name='Абдулино', region='Оренбургская область', district='Приволжский', population=20663, foundation=1795}\n" +
                "City{name='Алдан', region='Якутия', district='Дальневосточный', population=21277, foundation=1924}\n" +
                "City{name='Александровск-Сахалинский', region='Сахалинская область', district='Дальневосточный', population=10613, foundation=1869}\n" +
                "City{name='Амурск', region='Хабаровский край', district='Дальневосточный', population=42977, foundation=1958}\n" +
                "City{name='Агидель', region='Башкортостан', district='Приволжский', population=16365, foundation=1980}\n" +
                "City{name='Агрыз', region='Татарстан', district='Приволжский', population=19299, foundation=1646}\n" +
                "City{name='Москва', region='Московская область', district='Центральный', population=12655050, foundation=1147}\n" +
                "City{name='Санкт-Петербург', region='Ленинградская область', district='Северо-Западный', population=5384342, foundation=1703}";

        assertEquals(expected, outputStreamCaptor.toString().trim());

        System.setOut(standardOut);
    }

    @Test
    public void testSortingByName() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));


        CityService service = new CityService();
        CityFileParser cityFileParser = new CityFileParser(service.getRepository());

        cityFileParser.parseObjectsFromInputFile("cities.txt");
        service.setSort(new SortByNameAscIgnoreCase());
        service.sort();
        service.printAllCities();

        String expected = "City{name='Абаза', region='Хакасия', district='Сибирский', population=17111, foundation=1867}\n" +
                "City{name='Абакан', region='Хакасия', district='Сибирский', population=165183, foundation=1931}\n" +
                "City{name='Абдулино', region='Оренбургская область', district='Приволжский', population=20663, foundation=1795}\n" +
                "City{name='Агидель', region='Башкортостан', district='Приволжский', population=16365, foundation=1980}\n" +
                "City{name='Агрыз', region='Татарстан', district='Приволжский', population=19299, foundation=1646}\n" +
                "City{name='Адыгейск', region='Адыгея', district='Южный', population=12248, foundation=1973}\n" +
                "City{name='Алдан', region='Якутия', district='Дальневосточный', population=21277, foundation=1924}\n" +
                "City{name='Александровск-Сахалинский', region='Сахалинская область', district='Дальневосточный', population=10613, foundation=1869}\n" +
                "City{name='Амурск', region='Хабаровский край', district='Дальневосточный', population=42977, foundation=1958}\n" +
                "City{name='Горно-Алтайск', region='Алтай', district='Сибирский', population=56928, foundation=1830}\n" +
                "City{name='Майкоп', region='Адыгея', district='Южный', population=144246, foundation=1857}\n" +
                "City{name='Москва', region='Московская область', district='Центральный', population=12655050, foundation=1147}\n" +
                "City{name='Санкт-Петербург', region='Ленинградская область', district='Северо-Западный', population=5384342, foundation=1703}";

        assertEquals(expected, outputStreamCaptor.toString().trim());

        System.setOut(standardOut);
    }

    @Test
    public void testSortingByNameIgnoreCase() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        CityService service = new CityService();
        CityFileParser cityFileParser = new CityFileParser(service.getRepository());

        cityFileParser.parseObjectsFromInputFile("test/citiesDifferentCase.txt");
        service.setSort(new SortByNameAscIgnoreCase());
        service.sort();
        service.printAllCities();

        String expected = "City{name='aдыгейск', region='Адыгея', district='Южный', population=12248, foundation=1973}\n" +
                "City{name='АБаза', region='Хакасия', district='Сибирский', population=17111, foundation=1867}\n" +
                "City{name='Абакан', region='Хакасия', district='сибирский', population=165183, foundation=1931}\n" +
                "City{name='аБдулино', region='Оренбургская область', district='Приволжский', population=20663, foundation=1795}\n" +
                "City{name='Агидель', region='Башкортостан', district='Приволжский', population=16365, foundation=1980}\n" +
                "City{name='агрыз', region='Татарстан', district='Приволжский', population=19299, foundation=1646}\n" +
                "City{name='Алдан', region='Якутия', district='дальневосточный', population=21277, foundation=1924}\n" +
                "City{name='Александровск-Сахалинский', region='Сахалинская область', district='Дальневосточный', population=10613, foundation=1869}\n" +
                "City{name='Амурск', region='Хабаровский край', district='дальневосточный', population=42977, foundation=1958}\n" +
                "City{name='Горно-Алтайск', region='Алтай', district='сибирский', population=56928, foundation=1830}\n" +
                "City{name='Майкоп', region='Адыгея', district='южный', population=144246, foundation=1857}\n" +
                "City{name='Москва', region='Московская область', district='Центральный', population=12655050, foundation=1147}\n" +
                "City{name='Санкт-Петербург', region='Ленинградская область', district='Северо-Западный', population=5384342, foundation=1703}";

        assertEquals(expected, outputStreamCaptor.toString().trim());

        System.setOut(standardOut);

    }

    @Test
    public void testSortingByDistrictAndNameAsc() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        CityService service = new CityService();
        CityFileParser cityFileParser = new CityFileParser(service.getRepository());

        cityFileParser.parseObjectsFromInputFile("cities.txt");
        service.setSort(new SortByDistrictAndNameAscNotIgnoreCase());
        service.sort();
        service.printAllCities();

        String expected = "City{name='Алдан', region='Якутия', district='Дальневосточный', population=21277, foundation=1924}\n" +
                "City{name='Александровск-Сахалинский', region='Сахалинская область', district='Дальневосточный', population=10613, foundation=1869}\n" +
                "City{name='Амурск', region='Хабаровский край', district='Дальневосточный', population=42977, foundation=1958}\n" +
                "City{name='Абдулино', region='Оренбургская область', district='Приволжский', population=20663, foundation=1795}\n" +
                "City{name='Агидель', region='Башкортостан', district='Приволжский', population=16365, foundation=1980}\n" +
                "City{name='Агрыз', region='Татарстан', district='Приволжский', population=19299, foundation=1646}\n" +
                "City{name='Санкт-Петербург', region='Ленинградская область', district='Северо-Западный', population=5384342, foundation=1703}\n" +
                "City{name='Абаза', region='Хакасия', district='Сибирский', population=17111, foundation=1867}\n" +
                "City{name='Абакан', region='Хакасия', district='Сибирский', population=165183, foundation=1931}\n" +
                "City{name='Горно-Алтайск', region='Алтай', district='Сибирский', population=56928, foundation=1830}\n" +
                "City{name='Москва', region='Московская область', district='Центральный', population=12655050, foundation=1147}\n" +
                "City{name='Адыгейск', region='Адыгея', district='Южный', population=12248, foundation=1973}\n" +
                "City{name='Майкоп', region='Адыгея', district='Южный', population=144246, foundation=1857}";

        assertEquals(expected, outputStreamCaptor.toString().trim());

        System.setOut(standardOut);

    }

    @Test
    public void testSortingByDistrictAndNameAscNotIgnoreCase() {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        CityService service = new CityService();
        CityFileParser cityFileParser = new CityFileParser(service.getRepository());

        cityFileParser.parseObjectsFromInputFile("test/citiesDifferentCase.txt");
        service.setSort(new SortByDistrictAndNameAscNotIgnoreCase());
        service.sort();
        service.printAllCities();

        String expected = "City{name='Александровск-Сахалинский', region='Сахалинская область', district='Дальневосточный', population=10613, foundation=1869}\n" +
                "City{name='Агидель', region='Башкортостан', district='Приволжский', population=16365, foundation=1980}\n" +
                "City{name='аБдулино', region='Оренбургская область', district='Приволжский', population=20663, foundation=1795}\n" +
                "City{name='агрыз', region='Татарстан', district='Приволжский', population=19299, foundation=1646}\n" +
                "City{name='Санкт-Петербург', region='Ленинградская область', district='Северо-Западный', population=5384342, foundation=1703}\n" +
                "City{name='АБаза', region='Хакасия', district='Сибирский', population=17111, foundation=1867}\n" +
                "City{name='Москва', region='Московская область', district='Центральный', population=12655050, foundation=1147}\n" +
                "City{name='aдыгейск', region='Адыгея', district='Южный', population=12248, foundation=1973}\n" +
                "City{name='Алдан', region='Якутия', district='дальневосточный', population=21277, foundation=1924}\n" +
                "City{name='Амурск', region='Хабаровский край', district='дальневосточный', population=42977, foundation=1958}\n" +
                "City{name='Абакан', region='Хакасия', district='сибирский', population=165183, foundation=1931}\n" +
                "City{name='Горно-Алтайск', region='Алтай', district='сибирский', population=56928, foundation=1830}\n" +
                "City{name='Майкоп', region='Адыгея', district='южный', population=144246, foundation=1857}";

        assertEquals(expected, outputStreamCaptor.toString().trim());

        System.setOut(standardOut);
    }

    @Test
    public void testGetCityPositionWithMaxPopulation() {
        CityService service = new CityService();
        CityFileParser cityFileParser = new CityFileParser(service.getRepository());

        cityFileParser.parseObjectsFromInputFile("cities.txt");

        String expected = "[11]=12655050";
        assertEquals(expected, service.getCityPositionWithMaxPopulation());
    }

    @Test
    public void testCountCitiesPerRegion() {
        CityService service = new CityService();
        CityFileParser cityFileParser = new CityFileParser(service.getRepository());

        cityFileParser.parseObjectsFromInputFile("cities.txt");

        String expected = "Хакасия - 2\n" +
                "Башкортостан - 1\n" +
                "Оренбургская область - 1\n" +
                "Сахалинская область - 1\n" +
                "Татарстан - 1\n" +
                "Хабаровский край - 1\n" +
                "Якутия - 1\n" +
                "Адыгея - 2\n" +
                "Ленинградская область - 1\n" +
                "Московская область - 1\n" +
                "Алтай - 1";

        assertEquals(expected, service.countCitiesPerRegion().trim());
    }

}
