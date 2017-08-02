package com.example.kevin.giphy.search.injection;

import com.example.kevin.giphy.injection.components.ApplicationComponent;
import com.example.kevin.giphy.injection.components.BaseActivityComponent;
import com.example.kevin.giphy.injection.scopes.ActivityScope;
import com.example.kevin.giphy.search.view.GiphySearchActivity;

import dagger.Component;


@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = GiphySearchActivityModule.class)
public interface GiphySearchActivityComponent extends BaseActivityComponent<GiphySearchActivity> {
}
