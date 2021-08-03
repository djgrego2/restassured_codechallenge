package api_test;

import static io.restassured.RestAssured.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojo.GetAllCharactersInformation;

import java.util.Arrays;

public class TC_Rest_Assured_Code_Challenge {

    @BeforeTest
    public void setUp(){
        baseURI = "https://www.breakingbadapi.com";
        basePath = "/api";
        //filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); // LOGS
        requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();

    }

    @Test (priority = 1)
    public void PrintAllCharactersInformationFromPOJO(){

        Response response = get("/characters")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        GetAllCharactersInformation[] All_Characters_Information = response.getBody().as(GetAllCharactersInformation[].class);
        assertThat(Arrays.toString(All_Characters_Information), not(emptyString()));
        for(GetAllCharactersInformation Characters_Information:  All_Characters_Information){
            System.out.println(Characters_Information);
        }
    }

    @Test (priority = 2)
    public void GetWalterBirthday(){

        String WalterBirthday = given()
                .get("/characters/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                        .jsonPath()
                        .getString("birthday");

        assertThat(WalterBirthday,equalTo("[09-07-1958]"));
        System.out.println("Walter White Birthday is: " + WalterBirthday);
    }
}
