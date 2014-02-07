import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Point (x,y) in the Cartesian plane.
 * 
 * @author Adam Dziedzic
 * 
 */
class Point {

	/** x coordinate of the point */
	private double x;

	/** y coordinate of the point */
	private double y;

	/**
	 * Create a point.
	 * 
	 * @param x
	 *            x coordinate of the point
	 * @param y
	 *            y coordinate of the point
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return x coordinate of the point
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return y coordinate of the point
	 */
	public double getY() {
		return y;
	}

}

/**
 * Find max number of collinear points.
 * 
 * @author Adam Dziedzic
 * 
 */
public class CollinearPoints {

	/**
	 * ay = mx + b
	 * 
	 * @author Adam Dziedzic
	 * 
	 */
	private static class Parameters {
		/** slope - m parameter for x */
		double m;

		/** intercept - b parameter */
		double b;

		/**
		 * for the exception when the equation of the line is in the form: x =
		 * intercept
		 */
		double a;

		public Parameters(double m, double b) {
			this.m = m;
			this.b = b;
			this.a = 1; // standard value for equation in the form: y = mx + b
		}

		public Parameters(double b) {
			this.b = b;
			this.a = 0;
			this.m = 1; // equation of the form: x = b
		}

		public String toString() {
			return "m: " + m + " b: " + b + " a: " + a;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + CollinearPoints.class.hashCode();
			long temp;
			temp = Double.doubleToLongBits(a);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(b);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(m);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Parameters other = (Parameters) obj;
			if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a))
				return false;
			if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b))
				return false;
			if (Double.doubleToLongBits(m) != Double.doubleToLongBits(other.m))
				return false;
			return true;
		}
	}

	/**
	 * Get Parameters of a collinear line for the given two points.
	 * 
	 * @param a
	 *            first point
	 * @param b
	 *            second point
	 * @return parameter for the collinear line
	 */
	private static Parameters getParameters(Point a, Point b) {
		double changeX = a.getX() - b.getX();
		if (changeX == 0) {
			return new Parameters(a.getX());
		}
		double changeY = a.getY() - b.getY();
		double m = changeY / changeX;
		double i = (a.getY() + b.getY() - m * (a.getX() + b.getX())) / 2;
		return new Parameters(m, i);
	}

	/**
	 * Get number of collinear points in the given collection of points.
	 * 
	 * @param pointsCollection
	 *            a collection of points in 2D Cartesian plane
	 * @return number of collinear points
	 */
	public static int getCollinearPointsNumber(
			Collection<Point> pointsCollection) {
		if (pointsCollection.isEmpty()) {
			return 0;
		}
		if (pointsCollection.size() == 1) {
			return 1;
		}
		List<Point> points = new ArrayList<Point>(pointsCollection);
		Map<Parameters, Set<Point>> parametersCounters = new HashMap<Parameters, Set<Point>>();
		int maxCounter = 2; // minimal number of collinear points for two points
		for (int i = 0; i < points.size() - 1; ++i) {
			for (int j = i + 1; j < points.size(); ++j) {
				Parameters params = getParameters(points.get(i), points.get(j));
				Set<Point> collinearPoints = parametersCounters.get(params);
				if (collinearPoints == null) {
					collinearPoints = new HashSet<Point>();
				}
				collinearPoints.add(points.get(i));
				collinearPoints.add(points.get(j));
				if (collinearPoints.size() > maxCounter) {
					maxCounter = collinearPoints.size();
				}
				parametersCounters.put(params, collinearPoints);
			}
		}
		return maxCounter;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Collinear points.");
		Point aPoint = new Point(1, 1);
		Point bPoint = new Point(1, 4);
		Point cPoint = new Point(5, 5);
		System.out.println(getParameters(aPoint, bPoint));
		System.out.println(getParameters(aPoint, cPoint));
		System.out.println(getParameters(bPoint, cPoint));
		List<Point> collection1 = new ArrayList<Point>();
		System.out.println(CollinearPoints
				.getCollinearPointsNumber(collection1));
		collection1.add(aPoint);
		System.out.println(CollinearPoints
				.getCollinearPointsNumber(collection1));
		collection1.add(bPoint);
		collection1.add(cPoint);
		System.out.println(CollinearPoints
				.getCollinearPointsNumber(collection1));
		collection1.add(new Point(6, 6));
		System.out.println(CollinearPoints
				.getCollinearPointsNumber(collection1));
		collection1.add(new Point(0, 3.75));
		collection1.add(new Point(9, 6));
		System.out.println(CollinearPoints
				.getCollinearPointsNumber(collection1));
	}
}
