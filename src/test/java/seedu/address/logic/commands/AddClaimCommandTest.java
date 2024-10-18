package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.insurance.InsurancePlan;
import seedu.address.model.client.insurance.InsurancePlanFactory;
import seedu.address.model.client.insurance.claim.Claim;

import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CLIENT;

public class AddClaimCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexValidInsuranceIdValidClaim_success() throws Exception {
        // CARL (third client) has the basic insurance plan
        Client client = model.getFilteredClientList().get(INDEX_THIRD_CLIENT.getZeroBased());

        // Assume valid insurance plan
        int validInsuranceId = 0;
        InsurancePlan insurancePlan = InsurancePlanFactory.createInsurancePlan(validInsuranceId);
        Claim claim = new Claim("B1234", 100);

        client.getInsurancePlansManager().addClaimToInsurancePlan(insurancePlan, claim);

    }
}
