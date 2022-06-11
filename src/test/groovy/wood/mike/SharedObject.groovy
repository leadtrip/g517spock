package wood.mike

import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class SharedObject extends Specification{

    @Shared AtomicInteger sharedAtomicInteger = new AtomicInteger()
    AtomicInteger notSharedAtomicInteger = new AtomicInteger()

    @Shared AtomicBoolean sharedAtomicBoolean = new AtomicBoolean()
    AtomicBoolean notSharedAtomicBoolean = new AtomicBoolean()

    void "test one"() {
        expect:
            sharedAtomicInteger.incrementAndGet() == 1
            notSharedAtomicInteger.incrementAndGet() == 1
            !sharedAtomicBoolean.getAndSet(true)
            !notSharedAtomicBoolean.getAndSet(true)
    }

    void "test two"() {
        expect:
            sharedAtomicInteger.incrementAndGet() == 2
            notSharedAtomicInteger.incrementAndGet() == 1
            sharedAtomicBoolean.get()
            !notSharedAtomicBoolean.get()
    }
}
