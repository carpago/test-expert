package nl.belastingdienst.aig.pit;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import nl.belastingdienst.hit.pit.bo.Opdracht;
import nl.belastingdienst.hit.pit.client.OpdrachtStatus;
import nl.belastingdienst.hit.pit.client.PitConnectorClient;

public class OpdrachtFilter implements Filter {

	private Logger logger = Logger.getLogger(this.getClass());
	private static final String OPDRACHT = "opdracht";
	private FilterConfig filterConfig;
	

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		Opdracht opdracht = null;
		HttpServletRequest request = null;
		if (servletRequest instanceof HttpServletRequest) {
			request = (HttpServletRequest) servletRequest;

			OpdrachtStatus opdrachtStatus = PitConnectorClient.getOpdrachtStatus(request);
			try {
				opdracht = opdrachtStatus.getOpdracht();
			} catch (NullPointerException npe) {
				logger.info("opdrachtSTatus is null pointer en daarom kan opdracht niet worden opgehaald.!");
			}
			
			if (opdrachtStatus.isOpdrachtRequestUitgevoerd()) {
				if (opdrachtStatus.hasOpdracht()) {
					opdracht = opdrachtStatus.getOpdracht();
					request.setAttribute("opdracht", opdracht);
				} else if (opdrachtStatus.isOnbekendeOpdracht()) {
					request.setAttribute("opdracht", "geen opdracht");
				} else if (opdrachtStatus.isNietGeautoriseerdVoorOpdracht()) {
					request.setAttribute("opdracht", "niet geautoriseerd");
				} else {
					logger.info("9. [PitClientFilter] doFilter: onverwachte fout, throws UnavailableException");
				}
			}
		}

		chain.doFilter(servletRequest, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("1. OpdrachtFilter::init aangeroepen");
		this.filterConfig = filterConfig;
	}
}
