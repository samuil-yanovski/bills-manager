package yanovski.billsmanager.daogen;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table CATEGORY.
 */
public class Category implements java.io.Serializable {

    private Long id;
    /** Not-null value. */
    private String name;
    private int iconId;
    private int customId;
    private int priority;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Category() {
    }

    public Category(Long id) {
        this.id = id;
    }

    public Category(Long id, String name, int iconId, int customId, int priority) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
        this.customId = customId;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    // KEEP METHODS - put your custom methods here

    @Override
    public String toString() {
        return name;
    }
    // KEEP METHODS END

}
