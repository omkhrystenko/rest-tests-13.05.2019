package petstore;

public class Config {
    final static String BASE_URI = "https://petstore.swagger.io/v2"; //не изменяемая, в одном єкземпляре, и живет пока жив программы
    final static String GET_PET_BY_ID = "pet/{petId}";
    final static  String GET_PET_BY_STATUS = "pet/findByStatus";
    final static String CREATE_UPDATE_PET = "pet";
}
