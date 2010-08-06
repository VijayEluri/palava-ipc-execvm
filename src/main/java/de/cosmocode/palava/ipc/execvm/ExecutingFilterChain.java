/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.ipc.execvm;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCallFilterChain;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;


/**
 * A {@link IpcCallFilterChain} implementation that executes the given command.
 *
 * @author Willi Schoenborn
 */
enum ExecutingFilterChain implements IpcCallFilterChain {
    
    INSTANCE;
    
    private static final Logger LOG = LoggerFactory.getLogger(ExecutingFilterChain.class);
    
    @Override
    public Map<String, Object> filter(IpcCall call, IpcCommand command) throws IpcCommandExecutionException {
        final Map<String, Object> result = Maps.newLinkedHashMap();
        LOG.debug("Executing {}", command);
        
        try {
            command.execute(call, result);
        } catch (IpcCommandExecutionException e) {
            LOG.debug("An expected exception was thrown while executing " + command, e);
            throw e;
            /* CHECKSTYLE:OFF */
        } catch (RuntimeException e) {
            /* CHECKSTYLE:ON */
            LOG.error("An unexpected exception was thrown while executing " + command, e);
            throw e;
        }
        
        return result;
    }
    
}
