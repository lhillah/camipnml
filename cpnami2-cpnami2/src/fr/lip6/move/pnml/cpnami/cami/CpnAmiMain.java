
package fr.lip6.move.pnml.cpnami.cami;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import fr.lip6.move.pnml.cpnami.cami.utils.Consts;
import fr.lip6.move.pnml.cpnami.exceptions.CamiException;
import fr.lip6.move.pnml.cpnami.exceptions.ExitException;

/**
 * 
 * Main entry class of CAMI-PNML application. Retrieves command-line arguments
 * and pass them onto Runner.
 * 
 */
public final class CpnAmiMain {

	/**
	 * . constructor for CpnAmiMain
	 */
	private CpnAmiMain() {
	}

	/**
	 * Main program to launch the CPN-AMI to PNML model transformation with
	 * ISO/IEC 15909-2 PNML framework. It needs arguments to transfer to
	 * {@link fr.lip6.move.pnml.cpnami.cami.Runner#run(String[])} method. If the
	 * correct arguments are missing, Runner prints help message.
	 * 
	 * @param args
	 *            the command-line arguments.
	 * @throws ExitException
	 *             throws exit exception
	 */
	public static void main(String[] args) throws ExitException {
		final Runner myRunner = CamiFactory.SINSTANCE.createRunner();
		try {
			myRunner.run(args);
		} catch (CamiException e) {
			System.err.println("Main: Problem when launching Runner.");
			throw new ExitException(Consts.EXIT_FAILURE, e.getMessage());
		}
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.stop();
		return;
		// throw new ExitException(Consts.EXIT_SUCCESS);
	}

}
