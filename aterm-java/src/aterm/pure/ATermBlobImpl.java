/*
 * Copyright (c) 2002-2007, CWI and INRIA
 *
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the University of California, Berkeley nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package aterm.pure;

import java.util.List;

import jjtraveler.VisitFailure;
import shared.HashFunctions;
import shared.SharedObject;
import aterm.AFun;
import aterm.ATerm;
import aterm.ATermAppl;
import aterm.ATermBlob;
import aterm.ATermList;
import aterm.ATermPlaceholder;
import aterm.Visitable;
import aterm.Visitor;

public class ATermBlobImpl extends ATermImpl implements ATermBlob {
  private byte[] data;

  /**
   * @depricated Use the new constructor instead.
   * @param factory
   */
  protected ATermBlobImpl(PureFactory factory) {
    super(factory);
  }
  
  protected ATermBlobImpl(PureFactory factory, ATermList annos, byte[] data) {
    super(factory, annos);

    this.data = data;
    
    setHashCode(HashFunctions.doobs(new Object[]{annos, data}));
  }

  public int getType() {
    return ATerm.BLOB;
  }

  /**
   * @depricated Use the new constructor instead.
   * @param hashCode
   * @param annos
   * @param data
   */
  protected void init(int hashCode, ATermList annos, byte[] data) {
    super.init(hashCode, annos);
    this.data = data;
  }

  public SharedObject duplicate() {
	  return this;
  }

	public boolean equivalent(SharedObject obj){
		if(obj instanceof ATermBlob){
			ATermBlob peer = (ATermBlob) obj;
			if(peer.getType() != getType()) return false;
				
			return (peer.getBlobData() == data && peer.getAnnotations().equals(getAnnotations()));
		}
		
		return false;
	}

  protected boolean match(ATerm pattern, List<Object> list) {
    if (equals(pattern)) {
      return true;
    }

    if (pattern.getType() == ATerm.PLACEHOLDER) {
      ATerm type = ((ATermPlaceholder) pattern).getPlaceholder();
      if (type.getType() == ATerm.APPL) {
        ATermAppl appl = (ATermAppl) type;
        AFun afun = appl.getAFun();
        if (afun.getName().equals("blob")
            && afun.getArity() == 0
            && !afun.isQuoted()) {
          list.add(data);
          return true;
            }
      }
    }

    return super.match(pattern, list);
  }

  public byte[] getBlobData() {
    return data;
  }

  public int getBlobSize() {
    return data.length;
  }

  public ATerm setAnnotations(ATermList annos) {
    return getPureFactory().makeBlob(data, annos);
  }

  public Visitable accept(Visitor v) throws VisitFailure {
    return v.visitBlob(this);
  }

}
