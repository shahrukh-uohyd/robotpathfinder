#include "trajectory/basictrajectory.h"

namespace rpf {
    inline std::shared_ptr<Path> BasicTrajectory::get_path() {
        return path;
    }
    inline std::shared_ptr<const Path> BasicTrajectory::get_path() const {
        return path;
    }
    inline std::vector<BasicMoment>& BasicTrajectory::get_moments() {
        return moments;
    }
    inline const std::vector<BasicMoment>& BasicTrajectory::get_moments() const {
        return moments;
    }
    inline double BasicTrajectory::get_init_facing() const {
        return init_facing;
    }

    inline RobotSpecs& BasicTrajectory::get_specs() {
        return specs;
    }
    inline const RobotSpecs& BasicTrajectory::get_specs() const {
        return specs;
    }
    inline TrajectoryParams& BasicTrajectory::get_params() {
        return params;
    }
    inline const TrajectoryParams& BasicTrajectory::get_params() const {
        return params;
    }

    inline double BasicTrajectory::total_time() const {
        return moments[moments.size() - 1].time;
    }
    inline bool BasicTrajectory::is_tank() const {
        return params.is_tank;
    }

    BasicMoment BasicTrajectory::get(double time) const {
        int start = 0;
        int end = moments.size() - 1;
        int mid;

        if(time >= total_time()) {
            return moments[moments.size() - 1];
        }

        while(true) {
            mid = (start + end) / 2;
            double mid_time = moments[mid].time;

            if(mid_time == time || mid == moments.size() - 1 || mid == 0) {
                return moments[mid];
            }
            
            double next_time = moments[mid + 1].time;
            if(mid_time <= time && next_time >= time) {
                double f = (time - mid_time) / (next_time - mid_time);
                auto &current = moments[mid];
                auto &next = moments[mid + 1];
                return BasicMoment(rpf::lerp(current.dist, next.dist, f), rpf::lerp(current.vel, next.vel, f),
                        rpf::lerp(current.accel, next.accel, f), rpf::langle(current.heading, next.heading, f), time, init_facing);
            }
            if(mid_time < time) {
                start = mid;
            }
            else {
                end = mid;
            }
        }
    }
}
