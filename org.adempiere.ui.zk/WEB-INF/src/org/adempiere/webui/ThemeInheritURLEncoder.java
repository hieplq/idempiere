package org.adempiere.webui;

import java.util.logging.Level;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.compiere.util.CLogger;
import org.zkoss.web.servlet.Servlets;
import org.zkoss.web.servlet.http.Https;
import org.zkoss.web.servlet.http.Encodes;
import org.zkoss.web.servlet.http.Encodes.URLEncoder;
import org.zkoss.web.util.resource.ExtendletContext;

public class ThemeInheritURLEncoder implements URLEncoder {
	private static final CLogger logger = CLogger.getCLogger(ThemeInheritURLEncoder.class.getName());
	
	@Override
	public String encodeURL(ServletContext ctx, ServletRequest request, ServletResponse response, String url,
			URLEncoder defaultEncoder) throws Exception {
		String urlEncoded = encodeURL0(ctx, request, response, url);
		if (urlEncoded == null) {
			if (logger.isLoggable(Level.WARNING))
				logger.warning ("not found:" + url);
		}
		return urlEncoded;
	}
	
	/**
	 *  _urlenc0 also Encodes.encodeURL0 is private so no way to reuse it like SimpleUiFactory.getPageDefinition on ThemeInheritUiFactory
	 *  just copy code of Encodes.encodeURL0 here to reuse. when update need to consider copy again
	 * @param ctx
	 * @param request
	 * @param response
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	private static final String encodeURL0(ServletContext ctx, ServletRequest request, ServletResponse response,
			String uri) throws Exception {
		if (uri == null || uri.length() == 0)
			return uri; //keep as it is

		boolean ctxpathSpecified = false;
		if (uri.charAt(0) != '/') { //NOT relative to context path
			if (Servlets.isUniversalURL(uri))
				return uri; //nothing to do

			if (uri.charAt(0) == '~') { //foreign context
				final String ctxroot;
				if (uri.length() == 1) {
					ctxroot = uri = "/";
				} else if (uri.charAt(1) == '/') {
					ctxroot = "/";
					uri = uri.substring(1);
				} else {
					uri = '/' + uri.substring(1);
					final int j = uri.indexOf('/', 1);
					ctxroot = j >= 0 ? uri.substring(0, j) : uri;
				}

				final ExtendletContext extctx = Servlets.getExtendletContext(ctx, ctxroot.substring(1));
				if (extctx != null) {
					final int j = uri.indexOf('/', 1);
					return extctx.encodeURL(request, response, j >= 0 ? uri.substring(j) : "/");
				}

				final ServletContext newctx = ctx.getContext(ctxroot);
				if (newctx != null) {
					ctx = newctx;
				} else if (logger.isLoggable(Level.SEVERE)) {
					logger.severe ("Context not found: " + ctxroot);
				}
				ctxpathSpecified = true;
			} else if (Https.isIncluded(request) || Https.isForwarded(request)) {
				//if relative URI and being included/forwarded,
				//converts to absolute
				String pgpath = Https.getThisServletPath(request);
				if (pgpath != null) {
					int j = pgpath.lastIndexOf('/');
					if (j >= 0) {
						uri = pgpath.substring(0, j + 1) + uri;
					} else {
						if (logger.isLoggable(Level.WARNING))
							logger.warning ("The current page doesn't contain '/':" + pgpath);
					}
				}
			}
		}

		//locate by locale and browser if necessary
		uri = Servlets.locate(ctx, request, uri, null);

		//prefix context path
		if (!ctxpathSpecified && uri.charAt(0) == '/'
		//ZK-3131: do not prefix context path to relative protocol urls that starts with //
				&& !(uri.length() > 1 && uri.charAt(1) == '/') && (request instanceof HttpServletRequest)) {
			//Work around with a bug when we wrap Pluto's RenderRequest (1.0.1)
			String ctxpath = Https.getThisContextPath(request);
			if (ctxpath.length() > 0 && ctxpath.charAt(0) != '/')
				ctxpath = '/' + ctxpath;

			//Some Web server's ctxpath is "/"
			final int last = ctxpath.length() - 1;
			if (last >= 0 && ctxpath.charAt(last) == '/')
				ctxpath = ctxpath.substring(0, last);

			uri = ctxpath + uri;
		}

		int j = uri.indexOf('?');
		if (j < 0) {
			uri = Encodes.encodeURI(uri);
		} else {
			uri = Encodes.encodeURI(uri.substring(0, j)) + uri.substring(j);
		}
		//encode
		if (response instanceof HttpServletResponse)
			uri = ((HttpServletResponse) response).encodeURL(uri);
		return uri;
	}
}
