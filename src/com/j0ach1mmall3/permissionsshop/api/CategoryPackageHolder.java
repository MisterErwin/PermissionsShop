package com.j0ach1mmall3.permissionsshop.api;

import org.bukkit.Bukkit;

import java.util.List;

/**
 * @author j0ach1mmall3 (business.j0ach1mmall3@gmail.com)
 * @since 9/01/16
 */
public abstract class CategoryPackageHolder extends PathItem {
    protected final String guiName;
    protected final int guiSize;
    protected List<Category> categories;
    protected List<Package> packages;

    CategoryPackageHolder(String identifier, String guiName, int guiSize) {
        super(identifier);
        this.guiName = guiName;
        this.guiSize = guiSize;
    }

    public String getGuiName() {
        return this.guiName;
    }

    public int getGuiSize() {
        return this.guiSize;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public List<Package> getPackages() {
        return this.packages;
    }

    public Category getCategory(String identifier) {
        Bukkit.broadcastMessage("Getting Category " + identifier + " of " + this.identifier);
        for(Category category : this.categories) {
            if(category.getIdentifier().equals(identifier)) return category;
        }
        return null;
    }

    public Package getPackage(String identifier) {
        for(Package pckage : this.packages) {
            if(pckage.getIdentifier().equals(identifier)) return pckage;
        }
        return null;
    }

    public PathItem getPathItem(int position) {
        for(Category category : this.categories) {
            if(category.getItem().getPosition() == position) return category;
        }
        for(Package pckage : this.packages) {
            if(pckage.getItem().getPosition() == position) return pckage;
        }
        return null;
    }
}
