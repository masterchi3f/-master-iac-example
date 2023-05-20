# Example for Terraform infrastructure-as-code with Kotlin

Minimalistic example repository for Terraform Language source code generation with Kotlin.

Generated code may also be executed through Kotlin subshells.

## Library repository

The source code used to write this example code is contained in repository:

[**Terraform infrastructure-as-code with Kotlin**](https://github.com/masterchi3f/master-iac)

![Release](https://jitpack.io/v/masterchi3f/master-iac.svg)

The library uses Jitpack for package distribution. See configuration in:
- The dependency block of [build.gradle.kts](build.gradle.kts)
- README of above linked repository

## How to use the library

See different Kotlin files in [src/main/kotlin](src/main/kotlin):
- [Init.kt](src/main/kotlin/Init.kt) creates the [out](out)-folder and its contents, it downloads the Terraform binary and executes the init-command of Terraform. This downloads the specified providers and creates a state file.
- [Plan.kt](src/main/kotlin/Plan.kt) executes the Terraform plan-command. Good to check changes before really applying them.
- [Apply.kt](src/main/kotlin/Apply.kt) executes the Terraform apply-command. Changed are applied in the cloud.
- [Destroy.kt](src/main/kotlin/Destroy.kt) executes the Terraform destroy-command. Changed from before are being reverted.

[Executor.kt](https://github.com/masterchi3f/master-iac/blob/master/src/main/kotlin/uks/master/thesis/terraform/Executor.kt) Terraform commands are completed synchronous. It is possible to call them directly after each other. Example:
```kotlin
fun main() {
    // Download Terraform binary to out-directory
    Executor.downloadTerraformBinary(
        "https://releases.hashicorp.com/terraform/1.3.3/terraform_1.3.3_windows_386.zip",
        true
    )
    // Generate Terraform-files in out-directory (see method in Init.kt)
    generateTfFiles()
    // Setup workspace in out-directory
    Executor.init()
    // See changes
    Executor.plan()
    // Apply changes
    Executor.apply()
    // Revert changes
    Executor.destroy()
}
```
