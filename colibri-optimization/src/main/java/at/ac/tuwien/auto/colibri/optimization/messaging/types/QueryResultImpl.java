/**************************************************************************************************
 * Copyright (c) 2016, Automation Systems Group, Institute of Computer Aided Automation, TU Wien
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the Institute nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE INSTITUTE AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE INSTITUTE OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *************************************************************************************************/

package at.ac.tuwien.auto.colibri.optimization.messaging.types;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;

import at.ac.tuwien.auto.colibri.messaging.types.QueryResult;

public class QueryResultImpl extends QueryResult
{
	private Object[][] results = null;

	public Object[][] getResults()
	{
		return this.results;
	}

	@Override
	public void setContent(String content)
	{
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();

		InputStream is = new ByteArrayInputStream(content.getBytes());

		ResultSet rs = ResultSetFactory.fromJSON(is);

		List<String> vars = rs.getResultVars();

		while (rs.hasNext())
		{
			ArrayList<Object> temp = new ArrayList<Object>();

			list.add(temp);

			QuerySolution s = rs.nextSolution();

			for (int i = 0; i < vars.size(); i++)
			{
				String var = vars.get(i);

				RDFNode node = s.get(var);

				if (node.isLiteral())
				{
					if (node.asLiteral().getValue().equals(""))
						temp.add(null);
					else
						temp.add(node.asLiteral().getValue());
				}
				else
					temp.add(node.toString());
			}
		}

		this.results = new Object[list.size()][vars.size()];

		int c = 0;

		for (ArrayList<Object> l : list)
		{
			this.results[c++] = l.toArray();
		}
	}
}
