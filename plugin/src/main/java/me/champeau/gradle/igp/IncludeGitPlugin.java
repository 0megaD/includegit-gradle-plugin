/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package me.champeau.gradle.igp;

import me.champeau.gradle.igp.internal.DefaultIncludeGitExtension;
import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;
import org.gradle.api.provider.ProviderFactory;

import javax.inject.Inject;
import java.io.File;

public abstract class IncludeGitPlugin implements Plugin<Settings> {

    public static final long DEFAULT_INTERVAL = 24 * 3600 * 1000;
    public static final String REFRESH_GIT_REPOSITORIES_PROPERTY = "refresh.git.repositories";

    @Inject
    protected abstract ProviderFactory getProviders();

    @Override
    public void apply(Settings settings) {
        GitIncludeExtension gitRepositories = settings.getExtensions().create(GitIncludeExtension.class, "gitRepositories", DefaultIncludeGitExtension.class, settings);
        gitRepositories.getCheckoutsDirectory().set(new File(settings.getSettingsDir(), "checkouts"));
        gitRepositories.getRefreshIntervalMillis().convention(getProviders()
                .systemProperty(REFRESH_GIT_REPOSITORIES_PROPERTY)
                .map(s -> s.isEmpty() ? 0L : Long.parseLong(s))
                .orElse(DEFAULT_INTERVAL)
        );
        settings.getGradle().settingsEvaluated(s -> ((DefaultIncludeGitExtension)gitRepositories).writeCheckoutMetadata());
    }

}
