/**
 * 
 */
package xcon.project.atm;

import xcon.project.atm.state.AtmState;

public class AtmSession {

    public String password;
    public String amount;
    public int wrongPasswordCounter;
    public String passwordHideString;
    public Account account;
    public AtmState state;
}