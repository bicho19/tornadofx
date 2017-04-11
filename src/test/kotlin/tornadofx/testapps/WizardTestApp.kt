package tornadofx.testapps

import javafx.geometry.Pos
import tornadofx.*
import tornadofx.tests.Customer
import tornadofx.tests.CustomerModel

class WizardTestApp : App(WizardTestView::class)
class WizardWorkspaceApp : WorkspaceApp(CustomerWizard::class)

class WizardTestView : View("Wizard Test") {
    override val root = button("Create customer") {
        isDefaultButton = true
        action {
            find<CustomerWizard>().openModal()
        }
    }
}

class CustomerWizard : Wizard("Create customer", "Provide customer information") {
    val customer: CustomerModel by inject()

    init {
        customer.item = Customer()
        add(WizardStep1::class)
        add(WizardStep2::class)
        showSteps = true
        showHeader = true
        numberedSteps = true
        showStepsHeader = false
        enableStepLinks = true
    }

    override fun onSave() {
        println("Saving ${customer.item.name}")
    }
}

class WizardStep1 : View("Customer Data") {
    val customer: CustomerModel by inject()
    val wizard: CustomerWizard by inject()

    override val complete = customer.valid(customer.name, customer.zip, customer.city)

    override val root = form {
        fieldset(title) {
            field("Name") {
                textfield(customer.name).required()
            }
            field("Zip/City") {
                textfield(customer.zip) {
                    prefColumnCount = 7
                    required()
                }
                textfield(customer.city) {
                    required()
                }
            }
            hbox {
                alignment = Pos.BASELINE_RIGHT
                hyperlink("Skip") {
                    action {
                        wizard.currentPage = wizard.pages.last()
                    }
                }
            }
        }
    }

    override fun onSave() {
        customer.commit(customer.name, customer.zip, customer.city)
    }
}

class WizardStep2 : View("Contact person") {
    override val root = form {
        fieldset(title) {
            field("Name") {
                textfield()
            }
            field("Phone") {
                textfield()
            }
            field("Email") {
                textfield()
            }
        }
    }
}
