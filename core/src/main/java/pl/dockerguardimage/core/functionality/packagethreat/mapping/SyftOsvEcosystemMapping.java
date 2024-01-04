package pl.dockerguardimage.core.functionality.packagethreat.mapping;

public enum SyftOsvEcosystemMapping {
    APK("apk", "Alpine"),
    PUBS("pubs", "Pub"),
    DPKG("dpkg", "Debian"),
    DEPS_JSON_DOTNET("deps.json", "NuGet"),
    MIX_ELIXIR("mix", "Hex"),
    GO_MOD("go.mod", "Go"),
    GO_BINARIES("Go binaries", "Go"),
    CABAL_HASKELL("cabal", "Haskell"),
    STACK_HASKELL("stack", "Haskell"),
    JAR_JAVA("jar", "Maven"),
    EAR_JAVA("ear", "Maven"),
    WAR_JAVA("war", "Maven"),
    PAR_JAVA("par", "Maven"),
    SAR_JAVA("sar", "Maven"),
    NAR_JAVA("nar", "Maven"),
    NATIVE_IMAGE_JAVA("native-image", "Maven"),
    NPM_JAVASCRIPT("npm", "npm"),
    YARN_JAVASCRIPT("yarn", "npm"),
    VMLINZ_LINUX_KERNEL("vmlinz", "Linux"),
    KO_LINUX_KERNEL("ko", "Linux"),
    COMPOSER_PHP("composer", "Packagist"),
    WHEEL_PYTHON("wheel", "PyPI"),
    EGG_PYTHON("egg", "PyPI"),
    POETRY_PYTHON("poetry", "PyPI"),
    REQUIREMENTS_TXT_PYTHON("requirements.txt", "PyPI"),
    GEM_RUBY("gem", "RubyGems"),
    CARGO_LOCK_RUST("cargo.lock", "crates.io"),
    JAVA_ARCHIVE("java-archive", "Maven"),
    DEB("deb", "Debian"),
    PYTHON("python", "PyPI"),
    RPM("rpm", "Linux"),
    GO_MODULE("go-module", "Go")
    // Additional direct mappings can be added here if they exist
    ;

    private final String syftEcosystem;
    private final String osvEcosystem;

    SyftOsvEcosystemMapping(String syftEcosystem, String osvEcosystem) {
        this.syftEcosystem = syftEcosystem;
        this.osvEcosystem = osvEcosystem;
    }

    public String getSyftEcosystem() {
        return syftEcosystem;
    }

    public String getOsvEcosystem() {
        return osvEcosystem;
    }
}
