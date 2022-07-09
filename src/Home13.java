import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Home13 {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String URL = "https://jsonplaceholder.typicode.com/users";
    private static final Gson GSON = new Gson();
    private static User myUser = new User();

//    Напишите программу, которая будет взаимодействовать
//    с API https://jsonplaceholder.typicode.com.
//
//    Можно пользоваться стандартными возможностями Java (HttpURLConnection),
//    либо познакомиться самостоятельно со сторонними
//    библиотеками (Apache Fluent API, Apache HTTPClient, Jsoup).
//*****************************************************************************

//    Задание 1
//    Программа должна содержать методы для реализации следующего функционала:

//*****************************************************************************
//    создание нового объекта в https://jsonplaceholder.typicode.com/users.
//    Возможно, вы не увидите обновлений на сайте. Метод создания работает
//    правильно, если в ответ на JSON с объектом вернулся такой же JSON,
//    но с полем id со значением на 1 больше, чем самый большой id на сайте.
    public static String createObject() throws IOException, InterruptedException {
        // Создаем нового юзера******************************************
//        User myUser = new User();
//        myUser = new User();

//        myUser.setName("Alex");
//        myUser.setUsername("Alex64");
//        myUser.setEmail("dgdfg@gmail.com");
//
//        Address address = new Address();
//        address.setCity("Sumy");
//        address.setSuite("Suite 64");
//        myUser.setAddress(address);
//
//        myUser.setPhone("021-343-4645");
//        myUser.setWebsite("Alex.com.ua");
//
//        Geo geo = new Geo();
//        geo.setLat(64.00);
//        myUser.setGeo(geo);
//
//        Company company = new Company();
//        company.setName("Coca cola");
//        myUser.setCompany(company);
        //************************************

        final String requestBody = GSON.toJson(myUser);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .POST(HttpRequest.BodyPublishers
                        .ofString(requestBody))
                .build();
        final HttpResponse<String> response = CLIENT.send(request,
                HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
//*****************************************************************************
    // обновление объекта в https://jsonplaceholder.typicode.com/users.
    // Возможно, обновления не будут видны на сайте напрямую. Будем считать,
    // что метод работает правильно, если в ответ на запрос придет обновленный
    // JSON (он должен быть точно таким же, какой вы отправляли).
    private static String updateObject() {
        //********************************************************************

        //********************************************************************
        myUser.setName("Alex_64");
        final String requestBody = GSON.toJson(myUser);
        System.out.println("requestBody = " + requestBody);
        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(URL + "//" + id))
//                .uri(URI.create(URL))
                .uri(URI.create(String.format("%s/%d", URL, 11)))
//                .PUT()
                .POST(HttpRequest.BodyPublishers
                        .ofString(requestBody))
//                .header("Content-type", "application/json")
                .build();
        final HttpResponse<String> response;
        try {
            response = CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return (response.body());

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(String.format("%s/%d", URL, id)))
//                .PUT()
////                .GET()
//                .build();

    }

//*****************************************************************************
    //    удаление объекта из https://jsonplaceholder.typicode.com/users.
    //    Здесь будем считать корректным результат - статус из группы 2хх в
    //    ответ на запрос.
    private static void deleteObject() {
        // В запросе вместо типа запроса GET указываем Delete
    }

//*****************************************************************************
    // получение информации обо всех пользователях
    // https://jsonplaceholder.typicode.com/users
    private static List<User> allUsers() {
        List<User> userList;
        try {
//            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();
            HttpResponse<String> response = CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());

            userList = GSON.fromJson(response.body(),
                    new TypeToken<List<User>>(){}.getType());

//            for (User user :
//                 userList) {
//                System.out.println("user = " + user);
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return (userList);
    }


//*****************************************************************************
    // получение информации о пользователе с определенным id
    // https://jsonplaceholder.typicode.com/users/{id}
    private static User userById(int id) {
        User user = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/%d", URL, id)))
                .GET()
                .build();

            HttpResponse<String> response = CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
//            System.out.println("response.body() ById = " + response.body());
            user = GSON.fromJson(response.body(), User.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return (user);
    }
//*****************************************************************************
// получение информации о пользователе с опредленным username -
// https://jsonplaceholder.typicode.com/users?username={username}
    private static User[] userByUserName(String username) {
        User user[] = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("%s?username=%s",
                            URL, username)))
                    .GET()
                    .build();
            HttpResponse response = CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
//            System.out.println("response.body() ByName = " + response.body());
        user = GSON.fromJson((String) response.body(), User[].class);
//            user = GSON.fromJson(response.body(), new TypeToken<List<User>>);
//            List<User> list = new Gson().fromJson(response.body(),
//                    new TypeToken<List<User>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return (user);
//        return(GSON.fromJson(response.body(), User[].class));
    }

//*****************************************************************************

//    Задание 2
//    Дополните программу методом, который будет выводить все комментарии
//    к последнему посту определенного пользователя и записывать их в файл.

//    https://jsonplaceholder.typicode.com/users/1/posts
//    Последним будем считать пост с наибольшим id.

//    https://jsonplaceholder.typicode.com/posts/10/comments

//    Файл должен называться "user-X-post-Y-comments.json",
//    где Х - номер пользователя, Y - номер поста.
//*****************************************************************************

//    Задание 3
//    Дополните программу методом, который будет выводить все открытые
//    задачи для пользователя Х.

//    https://jsonplaceholder.typicode.com/users/1/todos.

//    Открытыми считаются все задачи, у которых completed = false.

//*****************************************************************************

    public static void main(String[] args) throws IOException, InterruptedException {
//*****************************************************************************
        // Создаем нового юзера Метод создания работает правильно, если в ответ
        // на JSON с объектом вернулся такой же JSON, но с полем id со
        // значением на 1 больше, чем самый большой id на сайте.
//        String user = Home13.createObject();
//        System.out.println("myUser = " + user);
//*****************************************************************************

        //    получение информации о пользователе с определенным
        //    id https://jsonplaceholder.typicode.com/users/{id}
//        System.out.println("Home13.userById(9) = " + Home13.userById(9));

//*****************************************************************************
        // получение информации о пользователе с опредленным username -
        // https://jsonplaceholder.typicode.com/users?username={username}

//        for(User user : userByUserName("Maxime_Nienow")) {
//            System.out.println("user = " + user);
//        }

//*****************************************************************************
        System.out.println("Answer = " + Home13.updateObject());
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************
//*****************************************************************************


//        List<User> userList = Home13.allUsers();
//        for (User user:
//             userList) {
//            System.out.println("user = " + user);
//        }

//*****************************************************************************


    }
}