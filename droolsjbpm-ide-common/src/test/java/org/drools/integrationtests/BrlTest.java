/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.integrationtests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.KnowledgeBase;
import org.kie.builder.KnowledgeBuilder;
import org.kie.builder.KnowledgeBuilderFactory;
import org.kie.builder.ResourceType;
import org.kie.io.ResourceFactory;
import org.kie.runtime.StatefulKnowledgeSession;

import static org.junit.Assert.*;

import org.drools.Person;

public class BrlTest {
   
    @Test
    public void testBrl() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add( ResourceFactory.newClassPathResource( "BrlRule.package", getClass() ), ResourceType.DRL );
        kbuilder.add( ResourceFactory.newClassPathResource( "BrlRule.brl", getClass() ), ResourceType.BRL );

        // the compiled package
        KnowledgeBase kbase = kbuilder.newKnowledgeBase();
        kbase = SerializationHelper.serializeObject( kbase );

        StatefulKnowledgeSession session = kbase.newStatefulKnowledgeSession();
        session.insert( new Person( "Bob" ) );

        assertEquals( 1, session.getObjects().size() );
        
        session.fireAllRules();
        // should have fired
        assertEquals( 0, session.getObjects().size() );
    }

}
