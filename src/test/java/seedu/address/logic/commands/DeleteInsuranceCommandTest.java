package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.client.insurance.InsurancePlanFactory.INVALID_PLAN_ID_MESSAGE;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.insurance.InsurancePlan;
import seedu.address.model.client.insurance.InsurancePlanFactory;

class DeleteInsuranceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests the execution of {@code DeleteInsuranceCommand} with a valid index and valid insurance ID.
     * This test verifies that when a valid client index and valid insurance plan is provided,
     * the valid insurance plan is successfully deleted from the specified client.
     */
    @Test
    public void execute_validIndexValidInsuranceId_success() throws Exception {
        // CARL (third client) has the basic insurance plan
        Client client = model.getFilteredClientList().get(INDEX_THIRD_CLIENT.getZeroBased());
        int insuranceId = 0;
        InsurancePlan planToBeDeleted = InsurancePlanFactory.createInsurancePlan(insuranceId);

        // Proceed to delete this insurance plan
        DeleteInsuranceCommand deleteInsuranceCommand =
                new DeleteInsuranceCommand(INDEX_THIRD_CLIENT, insuranceId);

        client.getInsurancePlansManager().deletePlan(planToBeDeleted);
        Client updatedClient = client;

        String deleteExpectedMessage = String.format(DeleteInsuranceCommand.MESSAGE_DELETE_INSURANCE_PLAN_SUCCESS,
                planToBeDeleted, Messages.format(client));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setClient(client, updatedClient);

        assertCommandSuccess(deleteInsuranceCommand, model, deleteExpectedMessage, expectedModel);
    }

    /**
     * Tests the execution of {@code DeleteInsuranceCommand} with an invalid index.
     * This test verifies that when an invalid client index is provided, the command throws a {@code CommandException}.
     */
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Index provided is greater than total number of clients
        Index indexOutOfBounds = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeleteInsuranceCommand deleteInsuranceCommand =
                new DeleteInsuranceCommand(indexOutOfBounds, 0);

        assertCommandFailure(deleteInsuranceCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    /**
     * Tests the execution of {@code DeleteInsuranceCommand} with an invalid insurance ID.
     * This test verifies that when the specified insurance plan does not exist,
     * the command throws a {@code CommandException}.
     */
    @Test
    public void execute_invalidInsuranceId_throwsCommandException() {
        int invalidInsuranceId = -1;
        DeleteInsuranceCommand deleteInsuranceCommand =
                new DeleteInsuranceCommand(INDEX_FIRST_CLIENT, invalidInsuranceId);

        assertCommandFailure(deleteInsuranceCommand, model, String.format(INVALID_PLAN_ID_MESSAGE));
    }

    /**
     * Tests the overriden equals method in {@code DeleteInsuranceCommand}
     * Ensures that it returns {@code true} when they are equal and {@code false} otherwise
     */
    @Test
    public void equals() {
        DeleteInsuranceCommand deleteInsuranceFirstCommand = new DeleteInsuranceCommand(INDEX_FIRST_CLIENT, 0);
        DeleteInsuranceCommand deleteInsuranceSecondCommand = new DeleteInsuranceCommand(INDEX_SECOND_CLIENT, 1);

        // same object -> returns true
        assertEquals(deleteInsuranceFirstCommand, deleteInsuranceFirstCommand);

        // same values -> returns true
        DeleteInsuranceCommand deleteInsuranceFirstCommandCopy = new DeleteInsuranceCommand(INDEX_FIRST_CLIENT, 0);
        assertEquals(deleteInsuranceFirstCommand, deleteInsuranceFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteInsuranceFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteInsuranceFirstCommand);

        // different client/insurance -> returns false
        assertNotEquals(deleteInsuranceFirstCommand, deleteInsuranceSecondCommand);
    }
}
