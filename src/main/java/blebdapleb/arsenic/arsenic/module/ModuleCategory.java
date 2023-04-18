package blebdapleb.arsenic.arsenic.module;

public enum ModuleCategory {
    CLIENT("Client"),
    MOVEMENT("Movement"),
    COMBAT("Combat"),
    RENDER("Render"),
    MISC("Miscellaneous");

    public String name;
    private ModuleCategory(String name)
    {
        this.name = name;
    }
}
