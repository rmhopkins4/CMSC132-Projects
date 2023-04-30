import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class HeavyBag<T> extends AbstractCollection<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Map<T, Integer> bag;
	
	
    public HeavyBag() {
    	this.bag = new HashMap<T, Integer>();
    }

    @Override
    public boolean add(T o) {
    	
    	// bag.compute(o, (key, value) -> value == null ? 1 : value + 1);
    	
    	if(bag.containsKey(o)) {
    		bag.put(o, bag.get(o) + 1);
    	} else {
    		bag.put(o, 1);
    	}
    	return true;
    }

    public boolean addMany(T o, int count) {
    	
    	// bag.compute(o, (key, value) -> value == null ? count : value + count);
    	
    	if(bag.containsKey(o)) {
    		bag.put(o, bag.get(o) + count);
    	} else {
    		bag.put(o, count);
    	}
    	return true;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if(!contains(o)) {
			return false;
		}
		
		int count = bag.get(o);
		if(count == 1) {
			bag.remove(o);
		} else {
			bag.put((T)o, count - 1);
		}
		return true;
	}

	@Override
	public boolean contains(Object o) {
		return bag.containsKey(o);
	}

	public Set<T> uniqueElements() {
    	return bag.keySet();
    }

    @Override
	public int size() {
		int size = 0;
		for(Integer count : bag.values()) {
			size += count;
		}
		return size;
	}

	public int getCount(Object o) {
		return bag.getOrDefault(o, 0);
	}

	public T choose(Random r) {
    	if(bag.isEmpty()) {
    		return null;
    	}
    	int randomNum = r.nextInt(size());
    	for(T key : bag.keySet()) {
    		randomNum -= bag.get(key);
    		if(randomNum < 0) {
    			return key;
    		}
    	}
    	return null;
    }

    @Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		if(!(o instanceof HeavyBag)) {
			return false;
		}
		
		HeavyBag<?> other = (HeavyBag<?>)o;
		return bag.equals(other.bag);
	}

	@Override
	public int hashCode() {
		return bag.hashCode();
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private Iterator<T> objIterator = bag.keySet().iterator();
			int pocketDepth = 0;
			T currentPocket = null;
			
			@Override
			public T next(){
				pocketDepth++;
				return currentPocket;
			}
			
			@Override
			public boolean hasNext() {
				if(currentPocket == null) {
					if(!objIterator.hasNext()) {
						return false;
					}
					currentPocket = objIterator.next();
					return true;
				}
				if(pocketDepth < bag.get(currentPocket)) {
					return true;
				}
				if(!objIterator.hasNext()) {
					return false;
				}
				currentPocket = objIterator.next();
				pocketDepth = 0;
				return true;
			}
			
			@Override
			public void remove() {
				bag.put(currentPocket, bag.get(currentPocket) - 1);
			}
		};
	}

	@Override
	public String toString() {
		String toReturn = "";
		for(T key : bag.keySet()) {
			toReturn += key.toString() + " : " + bag.get(key) + ", ";
		}
		return toReturn;
	}
}