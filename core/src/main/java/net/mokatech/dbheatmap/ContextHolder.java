package net.mokatech.dbheatmap;


public class ContextHolder {

    public static final String DEFAULT_CONTEXT = "default";
    private final ThreadLocal<String> currentContext = new ThreadLocal<>();

    public void setCurrentContext(String qualifier) {
        this.currentContext.set(qualifier);
    }

    public String getCurrentContext() {
        String context = currentContext.get();
        return context == null ? DEFAULT_CONTEXT : context;
    }
}
