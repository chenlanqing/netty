/*
 * Copyright 2019 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.resolver.dns.macos;

import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsServerAddressStreamProviders;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class MacOSDnsServerAddressStreamProviderTest {

    @BeforeClass
    public static void assume() {
        Assume.assumeTrue(MacOSDnsServerAddressStreamProvider.isAvailable());
    }

    @Test
    public void testStream() {
        DnsServerAddressStreamProvider provider = new MacOSDnsServerAddressStreamProvider();
        DnsServerAddressStream stream = provider.nameServerAddressStream("netty.io");
        assertNotNull(stream);
        assertNotEquals(0, stream.size());

        for (int i = 0; i < stream.size(); i++) {
            assertNotEquals(0, stream.next().getPort());
        }
    }

    @Test
    public void testDefaultUseCorrectInstance() {
        assertThat(DnsServerAddressStreamProviders.platformDefault(),
                instanceOf(MacOSDnsServerAddressStreamProvider.class));
    }

}
