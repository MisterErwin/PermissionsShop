package com.j0ach1mmall3.permissionsshop.api;

/**
 * @author j0ach1mmall3 (business.j0ach1mmall3@gmail.com)
 * @since 9/01/16
 */
public abstract class PathItem {
    protected final String identifier;
    protected CategoryPackageHolder parent;

    PathItem(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Shop getShop() {
        if(this.parent instanceof Shop) return (Shop) this.parent;
        return this.parent.getShop();
    }

    public CategoryPackageHolder getParent() {
        return this.parent;
    }

    public void setParent(CategoryPackageHolder parent) {
        this.parent = parent;
    }
}
