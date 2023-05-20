import uks.master.thesis.terraform.Executor
import uks.master.thesis.terraform.RootModule
import uks.master.thesis.terraform.lib.hcloud.v1_36_2.HCloud
import uks.master.thesis.terraform.lib.hcloud.v1_36_2.HCloudServer
import uks.master.thesis.terraform.lib.hcloud.v1_36_2.HCloudSshKey
import uks.master.thesis.terraform.syntax.elements.blocks.Terraform
import uks.master.thesis.terraform.syntax.expressions.TfFile
import uks.master.thesis.terraform.syntax.expressions.TfList

fun main() {
    Executor.downloadTerraformBinary(
        "https://releases.hashicorp.com/terraform/1.3.3/terraform_1.3.3_windows_386.zip",
        true
    )
    generateTfFiles()
    Executor.init()
}

fun generateTfFiles() {
    val terraform: Terraform = Terraform.Builder()
        .requiredVersion(">= 1.2.4")
        .requiredProviders(HCloud.requiredProviders("1.39.0"))
        .build()
    val hCloudSshKey: HCloudSshKey.Resource = HCloudSshKey.Resource.Builder()
        .resourceName("default")
        .name("hcloud_ssh_key")
        .publicKey(TfFile("~/.ssh/id_rsa.pub"))
        .build()
    val hCloudServer: HCloudServer.Resource = HCloudServer.Resource.Builder()
        .resourceName("test")
        .name("test")
        .image("ubuntu-22.04")
        .serverType("cx11")
        .location(HCloudServer.Location.NBG1)
        .publicNet(ipv4Enabled = true, ipv6Enabled = true)
        .sshKeys(
            TfList.Builder()
                .add(hCloudSshKey.id)
                .build()
        ).build()
    HCloud
        .addTokenToTfVarsFromEnv()
        .addProviderToRootModule()
    RootModule
        .setConfiguration(terraform)
        .addChild(HCloud.token)
        .addChild(hCloudSshKey)
        .addChild(hCloudServer)
        .generateFiles(true)
}
