package petstore.models;

public class PetModel {

    private int id;
    private CategoryModel category;
    private String name;
    private String[] photoUrls;
    private TagModel[] tags;
    private String status;

    public PetModel(int id, CategoryModel category, String name,
                    String[] photoUrls, TagModel[] tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public TagModel[] getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }
}
