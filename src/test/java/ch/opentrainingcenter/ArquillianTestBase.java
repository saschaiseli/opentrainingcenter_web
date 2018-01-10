package ch.opentrainingcenter;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;

public class ArquillianTestBase {
    @Deployment
    public static WebArchive createDeployment() {
        final WebArchive archive = ShrinkWrap.create(WebArchive.class); //
        archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        archive.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        archive.addAsLibraries(new File("src/main/webapp/WEB-INF/lib/fit_16.60.0.jar"));
        archive.addPackages(true, "ch.opentrainingcenter");
        final MavenResolverSystem resolver = Maven.resolver();
        final File[] files = resolver.loadPomFromFile("pom.xml").importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME, ScopeType.TEST).resolve()
                .withTransitivity().asFile();
        archive.addAsLibraries(files);
        return archive;
    }
}
